/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.ClienteJuridico;
import model.Item;
import model.Pedido;
import model.Produto;

/**
 *
 * @author bruni
 */
public class PedidosDAO {

    private static List<Pedido> Pedidos
            = new ArrayList<Pedido>();
    private static List<Cliente> Clientes
            = new ArrayList<Cliente>();
    private static List<Produto> Produtos
            = new ArrayList<Produto>();

    static {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Bruno");
        cliente.setTelefone("(47) 99998-8565");
        cliente.setLogin("bruno");
        cliente.setSenha("bruno");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Felipe");
        cliente2.setTelefone("(47) 99998-8565");
        cliente2.setLogin("felipe");
        cliente2.setSenha("felipe");

        Clientes.add(cliente);
        Clientes.add(cliente2);

        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Cerveja 1");
        produto.setQuantidadde(50);
        produto.setVlrUnit(7.5);

        Produto produto2 = new Produto();
        produto2.setId(2);
        produto2.setNome("Whisky");
        produto2.setQuantidadde(100);
        produto2.setVlrUnit(75.00);

        Produtos.add(produto);
        Produtos.add(produto2);

        Pedido pedido1 = new Pedido();
        ClienteJuridico cliJ = new ClienteJuridico();
        cliJ.setId(2);
        cliJ.setNome("Felipe");
        cliJ.setTelefone("(47) 99998-8565");
        cliJ.setLogin("felipe");
        cliJ.setSenha("felipe");
        cliJ.setCnpj("123456789");
        pedido1.setCliente(cliJ);
        Pedidos.add(pedido1);
    }

    public static void CriarPedido(Pedido pedido) throws ItemNaoCadastradoException, ItemNaoPossuiQuantidadeException, NaoHaItensNaListaException {

        boolean quantidadeMenor = false;
        boolean itemNaoExiste = true;

        double valorTotalPedido = 0;

        if (pedido.getItens().size() <= 0) {
            throw new NaoHaItensNaListaException();
        } else {

            for (Item item : pedido.getItens()) {
                itemNaoExiste = true;
                for (Produto produto : Produtos) {
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

                for (Item item : pedido.getItens()) {
                    for (Produto produto : Produtos) {
                        if (item.getProduto().getId() == produto.getId()) {
                            produto.setQuantidadde(produto.getQuantidadde() - item.getQuantidade());
                        }
                    }
                }
            }

            Pedidos.add(pedido);
        }
    }

    public static List<Pedido> ObterPedidos(long idCliente) {
        List<Pedido> retornoPedidos = new ArrayList<Pedido>();

        System.out.println(idCliente);

        if (idCliente == 0) {
            System.out.println("Entrou");
            retornoPedidos = Pedidos;
        } else {
            for (Pedido pedido : Pedidos) {
                if (pedido.getCliente().getId() == idCliente) {
                    retornoPedidos.add(pedido);
                }
            }
        }

        return retornoPedidos;
    }

    public static List<Produto> ObterProdutos(String nome, long id) {

        List<Produto> retornoProdutos = new ArrayList<Produto>();

        if (!nome.equals("")) {
            for (Produto produto : Produtos) {
                if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                    retornoProdutos.add(produto);
                }
            }
        }

        if (id != 0) {
            if (!nome.equals("")) {
                Produto produtoPorId = new Produto();
                for (Produto produto : retornoProdutos) {
                    if (produto.getId() == id) {
                        produtoPorId = produto;
                    }
                }
                retornoProdutos.clear();
                if (produtoPorId.getId() != 0) {
                    retornoProdutos.add(produtoPorId);
                }
            } else {
                for (Produto produto : Produtos) {
                    if (produto.getId() == id) {
                        retornoProdutos.add(produto);
                    }
                }
            }
        }

        if (nome.equals("") && id == 0) {
            return Produtos;
        }

        return retornoProdutos;
    }

    public static List<Cliente> ObterClientes() {
        return Clientes;
    }
}
