/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClienteNaoCadastradoException;
import dao.ItemNaoCadastradoException;
import dao.ItemNaoPossuiQuantidadeException;
import dao.NaoHaItensNaListaException;
import dao.PedidosDAO;
import dao.SenhaIncorretaException;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import model.Cliente;
import model.Pedido;
import model.Produto;

/**
 *
 * @author bruni
 */
@WebService
public class PedidosService {

    private static final String CAT = "cat";

    @WebResult(name = "Pedidos")
    public List<Pedido> listarPedidos(@WebParam(name = "codigo") long idCliente) {
        return PedidosDAO.ObterPedidos(idCliente);
    }

    @WebResult(name = "Produtos")
    public List<Produto> listarProdutos(@WebParam(name = "nome") String nomeProduto,
            @WebParam(name = "codigo") long idProduto
    ) {
        return PedidosDAO.ObterProdutos(nomeProduto, idProduto);
    }

    public void criarPedido(@WebParam(name = "pedido") Pedido pedido,
            @WebParam(name = "login", header = true) String login,
            @WebParam(name = "senha", header = true) String senha)
            throws UsuarioNaoAutenticadoException, ItemNaoCadastradoException, ItemNaoPossuiQuantidadeException, SenhaIncorretaException, ClienteNaoCadastradoException, NaoHaItensNaListaException {

        Cliente cliente = new Cliente();

        boolean senhaIncorreta = false;
        boolean clienteCadastrado = false;

        for (Cliente clienteAtual : PedidosDAO.ObterClientes()) {
            if (clienteAtual.getLogin().equals(login)) {
                if (clienteAtual.getSenha().equals(senha)) {
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
