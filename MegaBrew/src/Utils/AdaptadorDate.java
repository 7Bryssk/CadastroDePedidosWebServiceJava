/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author bruni
 */
public class AdaptadorDate extends XmlAdapter<XMLGregorianCalendar, Date> {

    @Override
    public Date unmarshal(XMLGregorianCalendar vt) throws Exception {
        Date date = vt.toGregorianCalendar().getTime();
        return date;
    }

    @Override
    public XMLGregorianCalendar marshal(Date bt) throws Exception {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(bt);

        XMLGregorianCalendar xml
                = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

        xml.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        xml.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

        return xml;
    }

}
