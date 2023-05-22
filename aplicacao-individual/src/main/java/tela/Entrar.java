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

        System.out.println(" \n"
                + "********Olá********\n\n"
                + "       / ..|\\\n"
                + "      (_\\  |_)\n"
                + "      /  \\@'\n"
                + "     /     \\\n"
                + " _  /  `   |\n"
                + "\\\\/  \\  | _\\\n"
                + " \\   /_ || \\\\_\n"
                + "  \\____)|_) \\_)");

        List<SelectModel> listaUsuario;
        List<SelectModel> listaUsuarioNuvem;
        List<Login> listaLeituraUsuario;
        List<Login> listaLeituraUsuarioNuvem;

        String email;
        
        do {
            System.out.println("\n\nDigite o seu email de usuário");
            email = leitor.nextLine();

            
            System.out.println(" ______________\n"
                    + "||            ||\n"
                    + "||            ||\n"
                    + "||Verificando ||\n"
                    + "||            ||\n"
                    + "||____________||\n"
                    + "|______________|\n"
                    + " \\\\############\\\\\n"
                    + "  \\\\############\\\\\n"
                    + "   \\      ____    \\   \n"
                    + "    \\_____\\___\\____\\");

            /*-------------------------------------------------------------------------*/
            //invocando o método selectDadosUsuario             
            listaUsuario = controller.selectDadosUsuarioLocal(email);
            System.out.println(listaUsuario);

            //invocando o método selectDadosUsuario             
            listaUsuarioNuvem = controller.selectDadosUsuarioNuvem(email);
            System.out.println(listaUsuarioNuvem);

            /*-------------------------------------------------------------------------*/
            //invocando o método selectLeituraUsuario
            listaLeituraUsuario = controller.selectLeituraUsuario(email);
            System.out.println(listaLeituraUsuario);

            //invocando o método selectLeituraUsuarioNuvem
            listaLeituraUsuarioNuvem = controller.selectLeituraUsuarioNuvem(email);
            System.out.println(listaLeituraUsuarioNuvem);

            /*-----------------------------------------------------------------------------*/
            if (listaUsuario.isEmpty() || listaUsuarioNuvem.isEmpty()) {

                System.out.println("\nUsuário não encontrado");

            }
        } while (listaUsuario.isEmpty() || listaUsuarioNuvem.isEmpty());

        if (!listaUsuario.isEmpty() && !listaUsuarioNuvem.isEmpty()) {

            System.out.println("\nBem-vindo de volta " + 
                    listaUsuario.get(0).getNomeUsuario() + " !");

            System.out.println("              ____\n"
                    + "            /____ `\\\n"
                    + "           ||_  _`\\ \\\n"
                    + "     .-.   `|O, O  ||\n"
                    + "     | |    (/    -)\\\n"
                    + "     | |    |`-'` |\\`\n"
                    + "  __/  |    | _/  |\n"
                    + " (___) \\.  _.\\__. `\\___\n"
                    + " (___)  )\\/  \\    _/  ~\\.\n"
                    + " (___) . \\   `--  _   `\\\n"
                    + "  (__)-    ,/        (   |\n"
                    + "       `--~|         |   |\n"
                    + "           |         |   | ");

            controller.inserirNoBanco(listaLeituraUsuario.get(0).getFkConfig(), listaLeituraUsuario.get(0).getFkComponente());
        }

    }

}
