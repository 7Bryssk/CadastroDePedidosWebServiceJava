/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClienteNaoCadastradoException;
import dao.ItemNaoCadastradoException;
import dao.ItemNaoPossuiQuantidadeException;
import dao.PedidosDAO;
import dao.SenhaIncorretaException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import model.Clientes;
import model.Pedidos;
import model.Produtos;

/**
 *
 * @author bruni
 */
@WebService
public class PedidosService {

    private static final String CAT = "cat";

    @WebResult(name = "Pedidos")
    public List<Pedidos> listarPedidos() {
        return PedidosDAO.ObterPedidos();
    }

    @WebResult(name = "Produtos")
    public List<Produtos> listarProdutos() {
        return PedidosDAO.ObterProdutos();
    }

    public void criarPedido(@WebParam(name = "pedido") Pedidos pedido,
            @WebParam(name = "cliente", header = true) Clientes usuario)
            throws UsuarioNaoAutenticadoException, ItemNaoCadastradoException, ItemNaoPossuiQuantidadeException, SenhaIncorretaException, ClienteNaoCadastradoException {

        Clientes cliente = new Clientes();

        boolean senhaIncorreta = false;
        boolean clienteCadastrado = false;

        for (Clientes clienteAtual : PedidosDAO.ObterClientes()) {
            if (clienteAtual.getLogin().equals(usuario.getLogin())) {
                if (clienteAtual.getSenha().equals(usuario.getSenha())) {
                    cliente = clienteAtual;
                    clienteCadastrado = true;
                } else {
                    senhaIncorreta = true;
                }
            }
        }

        if (senhaIncorreta) {
            throw new SenhaIncorretaException();
        } else if (!clienteCadastrado) {
            throw new ClienteNaoCadastradoException();
        } else {
            pedido.setCliente(cliente);
            PedidosDAO.CriarPedido(pedido);
        }

    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8181/pedidos",
                new PedidosService());
        System.out.println(" Serviço iniciado!");
    }
}
