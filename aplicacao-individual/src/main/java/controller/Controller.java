/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import service.ConexaoMySql;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import model.SelectModel;
import model.Leitura;
import model.Login;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import service.ConexaoMySqlServer;

/**
 *
 * @author BELLA
 */
public class Controller {

    //Instanciando conexao Banco Local - Mysql
    ConexaoMySql connectionLocal = new ConexaoMySql();
    JdbcTemplate conLocal = connectionLocal.getConnection();

    //Instanciando conexao Banco Nuvem - Azure
    ConexaoMySqlServer connectionNuvem = new ConexaoMySqlServer();
    JdbcTemplate conNuvem = connectionNuvem.getConnection();

    //Instanciando Looca + Classes monitoradas
    Looca looca = new Looca();

    //Instanciando Model de leitura - dados que vêm do looca
    Leitura leitura = new Leitura();

    //Grupo de discos
    List<Volume> listaDiscos = new ArrayList(looca.getGrupoDeDiscos().getVolumes());

    //Grupo de redes internet e wi-fi
    List<RedeInterface> listaRedes = new ArrayList(looca.getRede().getGrupoDeInterfaces().getInterfaces());

    //Select de dados do usuário - Login Local
    public List<SelectModel> selectDadosUsuarioLocal(String email) {

        List<SelectModel> listaUsuario = new ArrayList();

        listaUsuario = conLocal.query("SELECT * FROM tbUsuario WHERE emailUsuario = ?",
                new BeanPropertyRowMapper(SelectModel.class), email);

        return listaUsuario;
    }

    //Select de dados do usuário - Login Nuvem
    public List<SelectModel> selectDadosUsuarioNuvem(String email) {

        List<SelectModel> listaUsuarioNuvem = new ArrayList();

        listaUsuarioNuvem = conNuvem.query("SELECT * FROM tbUsuario WHERE emailUsuario = ?",
                new BeanPropertyRowMapper(SelectModel.class), email);

        return listaUsuarioNuvem;
    }

    /*-----------------------------------------------------------------------------------*/
    //Leituras do usuário - local
    public List<Login> selectLeituraUsuario(String email) {

        List<Login> listaLeituraUsuario = new ArrayList();

        listaLeituraUsuario = conLocal.query("select idLeitura , fkConfig, fkAlertaComponente , c.fkMaquina, fkComponente , nSerie ,  nomeUsuario from tbLeitura as l"
                + " join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina "
                + "join tbUsuario as u on u.fkMaquina = m.idMaquina where emailUsuario = ? order by idLeitura desc limit 1 ;",
                new BeanPropertyRowMapper(Login.class), email);

        return listaLeituraUsuario;
    }

    //Leituras do usuário - nuvem
    public List<Login> selectLeituraUsuarioNuvem(String email) {

        List<Login> listaLeituraUsuarioNuvem = new ArrayList();

        listaLeituraUsuarioNuvem = conNuvem.query(
                "select top 1 idLeitura, fkConfig, fkAlertaComponente ,c.fkMaquina, fkComponente ,nSerie , nomeUsuario"
                + " from tbLeitura as l join tbConfig as c on l.fkConfig = c.idConfig join tbMaquina as m on m.idMaquina = c.fkMaquina join tbUsuario as u on u.fkMaquina = m.idMaquina where emailUsuario = ? order by idLeitura desc ;",
                new BeanPropertyRowMapper(Login.class), email);

        return listaLeituraUsuarioNuvem;
    }

    /*----------------------------------------------------------------------------*/
    //Inserção de leituras - Local
    public void insertTbLeituraLocal(Integer fkConfig, Integer fkAlertaComponente) {

        conLocal.update("insert into tbLeitura values (?, ? ,? , ?, ?)",
                null, leitura.getLeitura(), leitura.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }

    //Inserção de leituras - Nuvem
    public void insertTbLeituraNuvem(Integer fkConfig, Integer fkAlertaComponente) {

        conNuvem.update("insert into tbLeitura(leitura, dataHoraLeitura , fkConfig, fkAlertaComponente) values (? ,? , ?, ?)",
                leitura.getLeitura(), leitura.getDataHoraLeitura(),
                fkConfig, fkAlertaComponente);
    }

    /*--------------------------------------------------------------------------------*/
    //Método de inserção no banco com timer task para inserir a cada x tempo
    public void inserirNoBanco(Integer fkConfig, Integer fkAlertaComponente) {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //data e hora 
                leitura.setDataHoraLeitura(LocalDateTime.now());

                //Uso memória
                leitura.setLeitura(looca.getMemoria().getEmUso().doubleValue());

                System.out.println("Memória em uso: " + leitura.getLeitura());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                //---------------------------------------------------------------------------//
                //Discos em uso
                for (Volume disco : listaDiscos) {

                    leitura.setLeitura(disco.getTotal().doubleValue()
                            - disco.getDisponivel().doubleValue());

                    System.out.println("Em uso do disco " + disco.getNome() + " "
                            + leitura.getLeitura());

                    insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                    insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                }

                //Redes em uso internet e wi-fi
                for (int i = listaRedes.size() - 1; i >= 0; i--) {

                    if (listaRedes.get(i).getBytesRecebidos().doubleValue() != 0
                         && listaRedes.get(i).getBytesEnviados().doubleValue() != 0) {

                        leitura.setLeitura(listaRedes.get(i).getBytesRecebidos().doubleValue());
                        leitura.setLeitura(listaRedes.get(i).getBytesEnviados().doubleValue());

                        System.out.println("-----------------------------------------------------");
                        System.out.println("Em uso da rede: " + listaRedes.get(i).getNome() + " : "
                                + leitura.getLeitura());

                        System.out.println("Bytes recebidos: " + listaRedes.get(i).getBytesRecebidos().doubleValue());
                        System.out.println("Bytes enviados: " + listaRedes.get(i).getBytesEnviados().doubleValue());
                        System.out.println("-----------------------------------------------------");

                        insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                        insertTbLeituraNuvem(fkConfig, fkAlertaComponente);

                    }
                }

                //---------------------------------------------------------------------------//
                //Uso processador
                leitura.setLeitura(looca.getProcessador().getUso().doubleValue());

                System.out.println("Processador em uso: " + leitura.getLeitura());

                insertTbLeituraLocal(fkConfig, fkAlertaComponente);
                insertTbLeituraNuvem(fkConfig, fkAlertaComponente);
            }
        }, 0, 100000);
    }

}
