/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.action;

import com.seta.entity.Docbandi;
import com.seta.entity.Docuserbandi;
import java.util.ArrayList;

/**
 *
 * @author raffaele
 */
public class Liste {

    public ArrayList<Docbandi> lid1 = null;
    public ArrayList<Docuserbandi> lidUser = null;
    
    public Liste() {
    }
    public Liste(String bando,String username) {
    
//        this.lid1 = ActionB.listaDocRichiesti(bando);
        this.lid1 = ActionB.listaDocRichiesti(bando,username);
        this.lidUser = ActionB.listaDocuserbando(bando, username);
    }

    public ArrayList<Docuserbandi> getLidUser() {
        return lidUser;
    }

    public void setLidUser(ArrayList<Docuserbandi> lidUser) {
        this.lidUser = lidUser;
    }
    
    public ArrayList<Docbandi> getLid1() {
        return lid1;
    }

    public void setLid1(ArrayList<Docbandi> lid1) {
        this.lid1 = lid1;
    }
    
    public String formatTipoDoc(String cod){
        for (int i = 0; i < lid1.size(); i++) {
            if (lid1.get(i).getCodicedoc().equals(cod)) {
                return lid1.get(i).getTitolo();
            }
        }
        return "-";
    }

}
