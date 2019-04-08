/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bruni
 */
@XmlEnum
@XmlType(name = "TipoEstabelecimento")
public enum Tipo {
    @XmlEnumValue("bar")
    BAR,
    @XmlEnumValue("pub")
    PUB,
    @XmlEnumValue("restaurante")
    RESTAURANTE,
    @XmlEnumValue("lanchonete")
    LANCHONETE;
}
