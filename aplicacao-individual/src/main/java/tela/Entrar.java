/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tela;

import controller.Controller;
import java.util.List;
import java.util.Scanner;
import model.Login;
import model.SelectModel;

/**
 *
 * @author BELLA
 */
public class Entrar {
    
    public static void main(String[] args) {

        Controller controller = new Controller();

        Scanner leitor = new Scanner(System.in);

        System.out.println("Digite o nome de Usuario");
        String usuario = leitor.nextLine();

        System.out.println("Digite a senha do Usuario");
        String senha = leitor.nextLine();

        /*-------------------------------------------------------------------------*/
        //invocando o método selectDadosUsuario             
        List<SelectModel> listaUsuario = controller.selectDadosUsuarioLocal(usuario, senha);
        System.out.println(listaUsuario);

        //invocando o método selectDadosUsuario             
        List<SelectModel> listaUsuarioNuvem = controller.selectDadosUsuarioNuvem(usuario, senha);
        System.out.println(listaUsuarioNuvem);

        /*-------------------------------------------------------------------------*/
        //invocando o método selectLeituraUsuario
        List<Login> listaLeituraUsuario = controller.selectLeituraUsuario(usuario, senha);
        System.out.println(listaLeituraUsuario);

        //invocando o método selectLeituraUsuarioNuvem
        List<Login> listaLeituraUsuarioNuvem = controller.selectLeituraUsuarioNuvem(usuario, senha);
        System.out.println(listaLeituraUsuarioNuvem);

        /*-----------------------------------------------------------------------------*/
        if (listaUsuario.isEmpty() || listaUsuarioNuvem.isEmpty()) {

            System.out.println("Usuário não encontrado");
        } else {

            System.out.println("Bem-vindo de volta " + usuario);

            controller.inserirNoBanco(listaLeituraUsuario.get(0).getFkConfig(), listaLeituraUsuario.get(0).getFkComponente());
        }

    }
    
}
