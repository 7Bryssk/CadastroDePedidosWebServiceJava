/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import model.Clientes;
import model.Itens;
import model.Pedidos;
import model.Produtos;

/**
 *
 * @author bruni
 */
public class PedidosDAO {

    private static List<Pedidos> Pedidos
            = new ArrayList<Pedidos>();
    private static List<Clientes> Clientes
            = new ArrayList<Clientes>();
    private static List<Produtos> Produtos
            = new ArrayList<Produtos>();

    static {
        Clientes cliente = new Clientes();
        cliente.setNome("Bruno");
        cliente.setTelefone("(47) 99998-8565");
        cliente.setLogin("bruno");
        cliente.setSenha("bruno");

        Clientes cliente2 = new Clientes();
        cliente2.setNome("Felipe");
        cliente2.setTelefone("(47) 99998-8565");
        cliente2.setLogin("felipe");
        cliente2.setSenha("pedreli");

        Clientes.add(cliente);
        Clientes.add(cliente2);

        Produtos produto = new Produtos();
        produto.setId(1);
        produto.setNome("Cerveja 1");
        produto.setQuantidadde(50);
        produto.setVlrUnit(7.5);

        Produtos produto2 = new Produtos();
        produto2.setId(2);
        produto2.setNome("Whisky");
        produto2.setQuantidadde(100);
        produto2.setVlrUnit(75.00);

        Produtos.add(produto);
        Produtos.add(produto2);

    }

    public static List<Pedidos> ObterPedidos() {
        return Pedidos;
    }

    public static void CriarPedido(Pedidos pedido) throws ItemNaoCadastradoException, ItemNaoPossuiQuantidadeException, NaoHaItensNaListaException {

        boolean quantidadeMenor = false;
        boolean itemNaoExiste = true;

        double valorTotalPedido = 0;

        if (pedido.getItens().size() <= 0) {
            throw new NaoHaItensNaListaException();
        } else {

            for (Itens item : pedido.getItens()) {
                itemNaoExiste = true;
                for (Produtos produto : Produtos) {
                    if (item.getProduto().getId() == produto.getId()) {
                        item.setProduto(produto);
                        if (item.getQuantidade() > produto.getQuantidadde()) {
                            quantidadeMenor = true;
                        }
                        itemNaoExiste = false;
                    }
                }

                if (!itemNaoExiste) {
                    item.setVlrTotal(item.getQuantidade() * item.getProduto().getVlrUnit());
                    valorTotalPedido = valorTotalPedido + item.getVlrTotal();
                }
            }

            pedido.setValorTotalPedido(valorTotalPedido);

            if (itemNaoExiste) {
                throw new ItemNaoCadastradoException();
            }

            if (quantidadeMenor) {
                throw new ItemNaoPossuiQuantidadeException();
            }

            if (!quantidadeMenor && !itemNaoExiste) {

                for (Itens item : pedido.getItens()) {
                    for (Produtos produto : Produtos) {
                        if (item.getProduto().getId() == produto.getId()) {
                            produto.setQuantidadde(produto.getQuantidadde() - item.getQuantidade());
                        }
                    }
                }
            }

            Pedidos.add(pedido);
        }
    }

    public static List<Produtos> ObterProdutos() {
        return Produtos;
    }

    public static List<Clientes> ObterClientes() {
        return Clientes;
    }
}
