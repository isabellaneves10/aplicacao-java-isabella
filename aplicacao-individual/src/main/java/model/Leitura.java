/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author BELLA
 */
public class Leitura {
    
    private Integer leituraId;
    private Double leitura;
    private LocalDateTime dataHoraLeitura;
    private Integer configFk;
    private Integer alertaComponenteFk;

    public Leitura(Integer leituraId, Double leitura, Integer configFk, Integer alertaComponenteFk) {
        this.leituraId = leituraId;
        this.leitura = leitura;
        this.configFk = configFk;
        this.alertaComponenteFk = alertaComponenteFk;
    }

    public Leitura() {

    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public Integer getLeituraId() {
        return leituraId;
    }

    public Double getLeitura() {
        return leitura;
    }

    public Integer getConfigFk() {
        return configFk;
    }

    public Integer getAlertaComponenteFk() {
        return alertaComponenteFk;
    }

    public void setDataHoraLeitura(LocalDateTime dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public void setLeituraId(Integer leituraId) {
        this.leituraId = leituraId;
    }

    public void setLeitura(Double leitura) {
        this.leitura = leitura;
    }

    public void setConfigFk(Integer configFk) {
        this.configFk = configFk;
    }

    public void setAlertaComponenteFk(Integer alertaComponenteFk) {
        this.alertaComponenteFk = alertaComponenteFk;
    }

    @Override
    public String toString() {
        return "\nLeituraModel:"
                + "\nleituraId: " + leituraId
                + "\nleitura=" + leitura
                + "\nconfigFk=" + configFk
                + "\nalertaComponenteFk:" + alertaComponenteFk;
    }
    
}
