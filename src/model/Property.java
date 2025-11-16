/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vukona-Maritz
 * 
 */

package model;

import java.io.Serializable;

public class Property implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pRefNo, propType, area, agentname, agency, aCust;
    private double sellingPrice;
    private boolean isDepositReq;

    public Property() {
        pRefNo = propType = area = agentname = agency = aCust = "";
        sellingPrice = 0;
        isDepositReq = false;
    }

    public Property(String pRefNo, String propType, String area, String agentname,
                    String agency, double sellingPrice, boolean isDepositReq, String aCust) {
        this.aCust = aCust;
        this.pRefNo = pRefNo;
        this.isDepositReq = isDepositReq;
        this.area = area;
        setPropType(propType);
        setAgency(agency);
        setAgentName(agentname);
        setSellingPrice(sellingPrice);
    }

    // Getters and setters
    public void setPropType(String propType) { this.propType = propType; }
    public String getPropType() { return propType; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getCustomer() { return aCust; }
    public void setCustomer(String aCust) { this.aCust = aCust; }

    public void setAgentName(String agentname) { this.agentname = agentname; }
    public String getAgentName() { return agentname; }

    public String getPRefNo() { return pRefNo; }
    public void setPRefNo(String pRefNo) { this.pRefNo = pRefNo; }

    public void setAgency(String agency) { this.agency = agency; }
    public String getAgency() { return agency; }

    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public double getSellingPrice() { return sellingPrice; }

    public void setIsDepositReq(boolean isDepositReq) { this.isDepositReq = isDepositReq; }
    public boolean getIsDepositReq() { return isDepositReq; }

    // --- Calculation methods ---
    public double calcDeposit() {
        return isDepositReq ? sellingPrice * 0.10 : 0.0;
    }

    public double getLoan() {
        return sellingPrice - calcDeposit();
    }

    public double calcMonthlyInstalment() {
        double monthlyRate = (10.5 / 100.0) / 12.0; // 10.5% annual interest
        int termMonths = 20 * 12; // 20 years
        double loanAmount = getLoan();
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -termMonths));
    }

    public double totPayment() {
        return calcMonthlyInstalment() * 20 * 12;
    }

    public double totInterest() {
        return totPayment() - getLoan();
    }
}
