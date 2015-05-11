/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.divudi.bean.report;

import com.divudi.bean.common.SessionController;
import com.divudi.bean.common.UtilityController;
import com.divudi.bean.memberShip.PaymentSchemeController;
import com.divudi.data.BillClassType;
import com.divudi.data.BillType;
import com.divudi.data.DepartmentType;
import com.divudi.data.FeeType;
import com.divudi.data.PaymentMethod;
import com.divudi.data.dataStructure.DatedBills;
import com.divudi.data.dataStructure.PharmacyDetail;
import com.divudi.data.dataStructure.PharmacyPaymetMethodSummery;
import com.divudi.data.dataStructure.PharmacySummery;
import com.divudi.data.dataStructure.SearchKeyword;
import com.divudi.data.table.String1Value3;
import com.divudi.data.table.String1Value6;
import com.divudi.data.table.String2Value4;
import com.divudi.ejb.BillReportBean;
import com.divudi.ejb.CommonFunctions;
import com.divudi.entity.Bill;
import com.divudi.entity.BillItem;
import com.divudi.entity.BilledBill;
import com.divudi.entity.CancelledBill;
import com.divudi.entity.Category;
import com.divudi.entity.Department;
import com.divudi.entity.Institution;
import com.divudi.entity.Item;
import com.divudi.entity.PaymentScheme;
import com.divudi.entity.PreBill;
import com.divudi.entity.RefundBill;
import com.divudi.entity.pharmacy.Amp;
import com.divudi.entity.pharmacy.ItemBatch;
import com.divudi.entity.pharmacy.PharmaceuticalBillItem;
import com.divudi.entity.pharmacy.Stock;
import com.divudi.facade.AmpFacade;
import com.divudi.facade.BillFacade;
import com.divudi.facade.BillItemFacade;
import com.divudi.facade.DepartmentFacade;
import com.divudi.facade.ItemBatchFacade;
import com.divudi.facade.ItemFacade;
import com.divudi.facade.StockFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TemporalType;
import org.eclipse.persistence.config.ResultType;

/**
 *
 * @author safrin
 */
@Named
@SessionScoped
public class PharmacySaleReport implements Serializable {

    Category category;
    Item item;
    private Date fromDate;
    private Date toDate;
    List<String1Value6> saleValuesCash;
    List<String1Value6> saleValuesCheque;
    List<String1Value6> saleValuesSlip;
    List<String1Value6> saleValuesCard;
    List<String1Value6> saleValuesCredit;
    List<String1Value6> wholeSaleValuesCash;
    List<String1Value6> wholeSaleValuesCheque;
    List<String1Value6> wholeSaleValuesSlip;
    List<String1Value6> wholeSaleValuesCard;
    List<String1Value6> wholeSaleValuesCredit;
    List<String1Value6> bhtIssues;
    List<String1Value6> unitIssues;
    List<BillItem> billItems;
    private Department department;
    Department toDepartment;
    private Institution institution;
    private double grantNetTotal;
    private double grantTotal;
    private double grantProfessional;
    private double grantDiscount;
    private double grantCashTotal;
    private double grantCreditTotal;
    private double grantCardTotal;
    ///////
    private PharmacySummery billedSummery;
    private PharmacyDetail billedDetail;
    private PharmacyDetail cancelledDetail;
    private PharmacyDetail refundedDetail;
    private PharmacyPaymetMethodSummery billedPaymentSummery;
    //  private List<DatedBills> billDetail;

    SearchKeyword searchKeyword;

    ///pharmacy summery all///
    double totalPSCashBV = 0.0;
    double totalPSCashCV = 0.0;
    double totalPSCashRV = 0.0;
    double totalPSCashNV = 0.0;
    double totalPSCashBC = 0.0;
    double totalPSCashRC = 0.0;
    double totalPSCashNC = 0.0;

    double totalPSCreditBV = 0.0;
    double totalPSCreditCV = 0.0;
    double totalPSCreditRV = 0.0;
    double totalPSCreditNV = 0.0;
    double totalPSCreditBC = 0.0;
    double totalPSCreditRC = 0.0;
    double totalPSCreditNC = 0.0;

    double totalPSCardBV = 0.0;
    double totalPSCardCV = 0.0;
    double totalPSCardRV = 0.0;
    double totalPSCardNV = 0.0;
    double totalPSCardBC = 0.0;
    double totalPSCardRC = 0.0;
    double totalPSCardNC = 0.0;

    double totalPSSlipBV = 0.0;
    double totalPSSlipCV = 0.0;
    double totalPSSlipRV = 0.0;
    double totalPSSlipNV = 0.0;
    double totalPSSlipBC = 0.0;
    double totalPSSlipRC = 0.0;
    double totalPSSlipNC = 0.0;

    double totalPSChequeBV = 0.0;
    double totalPSChequeCV = 0.0;
    double totalPSChequeRV = 0.0;
    double totalPSChequeNV = 0.0;
    double totalPSChequeBC = 0.0;
    double totalPSChequeRC = 0.0;
    double totalPSChequeNC = 0.0;

    ///pharmacy whole sale
    double totalPWSCashBV = 0.0;
    double totalPWSCashCV = 0.0;
    double totalPWSCashRV = 0.0;
    double totalPWSCashNV = 0.0;
    double totalPWSCashBC = 0.0;
    double totalPWSCashRC = 0.0;
    double totalPWSCashNC = 0.0;

    double totalPWSCreditBV = 0.0;
    double totalPWSCreditCV = 0.0;
    double totalPWSCreditRV = 0.0;
    double totalPWSCreditNV = 0.0;
    double totalPWSCreditBC = 0.0;
    double totalPWSCreditRC = 0.0;
    double totalPWSCreditNC = 0.0;

    double totalPWSCardBV = 0.0;
    double totalPWSCardCV = 0.0;
    double totalPWSCardRV = 0.0;
    double totalPWSCardNV = 0.0;
    double totalPWSCardBC = 0.0;
    double totalPWSCardRC = 0.0;
    double totalPWSCardNC = 0.0;

    double totalPWSSlipBV = 0.0;
    double totalPWSSlipCV = 0.0;
    double totalPWSSlipRV = 0.0;
    double totalPWSSlipNV = 0.0;
    double totalPWSSlipBC = 0.0;
    double totalPWSSlipRC = 0.0;
    double totalPWSSlipNC = 0.0;

    double totalPWSChequeBV = 0.0;
    double totalPWSChequeCV = 0.0;
    double totalPWSChequeRV = 0.0;
    double totalPWSChequeNV = 0.0;
    double totalPWSChequeBC = 0.0;
    double totalPWSChequeRC = 0.0;
    double totalPWSChequeNC = 0.0;

    double totalBHTIssueBV = 0.0;
    double totalBHTIssueCV = 0.0;
    double totalBHTIssueRV = 0.0;
    double totalBHTIssueNV = 0.0;
    double totalBHTIssueBC = 0.0;
    double totalBHTIssueRC = 0.0;
    double totalBHTIssueNC = 0.0;

    double totalUnitIssueBV = 0.0;
    double totalUnitIssueCV = 0.0;
    double totalUnitIssueRV = 0.0;
    double totalUnitIssueNV = 0.0;
    double totalUnitIssueBC = 0.0;
    double totalUnitIssueRC = 0.0;
    double totalUnitIssueNC = 0.0;

    List<Bill> labBhtIssueBilledBills;
    double labBhtIssueBilledBillTotals;
    /////

    /////
    @EJB
    private CommonFunctions commonFunctions;
    @EJB
    private BillItemFacade billItemFacade;
    @EJB
    private BillFacade billFacade;
    @EJB
    BillReportBean billReportBean;

    PaymentScheme paymentScheme;
    PaymentMethod paymentMethod;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    List<Item> nonMovingItems;

    @EJB
    StockFacade stockFacade;
    @EJB
    ItemFacade itemFacade;
    @EJB
    AmpFacade ampFacade;

    List<Amp> amps;
    List<Item> items;
    Department departmentMoving;

    public List<Item> getNonMovingItems() {
        return nonMovingItems;
    }

    public void setNonMovingItems(List<Item> nonMovingItems) {
        this.nonMovingItems = nonMovingItems;
    }

    public void fillNonMoving() {
        Map allItems;
        List<Item> movedItems;
        Stock s = new Stock();

//        s.getDepartment();
//        s.getItemBatch().getItem();
//        s.getItemBatch().getItem();
        HashMap m = new HashMap();
        m.put("dpt", getDepartmentMoving());
        String j;

        j = "select s.itemBatch.item "
                + " from Stock s "
                + " where s.stock > 0 "
                + " and s.department=:dpt "
                + " group by s.itemBatch.item "
                + " order by s.itemBatch.item.name";

        nonMovingItems = itemFacade.findBySQL(j, m);
        System.out.println("nonMovingItems = " + nonMovingItems);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void makeNull() {
        fromDate = null;
        category = null;
        toDate = null;
        department = null;
        grantNetTotal = 0;
        grantDiscount = 0;
        grantCardTotal = 0;
        grantCashTotal = 0;
        grantCreditTotal = 0;
        billedSummery = null;
        billedPaymentSummery = null;
        items = null;
        amps = null;

    }

    public Department getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(Department toDepartment) {
        this.toDepartment = toDepartment;
    }

//    private double getSaleValueByDepartment(Date date) {
//        //   List<Stock> billedSummery;
//        Date fd = getCommonFunctions().getStartOfDay(date);
//        Date td = getCommonFunctions().getEndOfDay(date);
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fd", fd);
//        m.put("td", td);
//        m.put("btp", BillType.PharmacyPre);
//        m.put("refType", BillType.PharmacySale);
//        sql = "select sum(i.netTotal) from Bill i where i.department=:d and i.referenceBill.billType=:refType "
//                + " and i.billType=:btp and i.createdAt between :fd and :td ";
//        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//        //System.err.println("from " + fromDate);
//        //System.err.println("Sale Value " + saleValue);
//        return saleValue;
//
//    }
    public void createGRNBillItemTable() {
//select bi from BillItem bi where  bi.retired=false  and bi.bill.billType=:bt  and bi.bill.createdAt bettween :fd and :td  and bi.bill.depId like :di  and bi.bill.referenceBill.deptId like :po;
        String sql;
        Map m = new HashMap();
        sql = "select bi from BillItem bi "
                + " where bi.bill.billType=:bt "
                + " and bi.bill.retired=false "
                + " and bi.bill.createdAt between :fd and :td ";

        if (searchKeyword.getBillNo() != null && !searchKeyword.getBillNo().toUpperCase().trim().equals("")) {
            sql += " and (upper(bi.bill.depId) like :di) ";
            m.put("di", "%" + searchKeyword.getBillNo().toUpperCase().trim() + "%");
        }
//        BillItem bi = new BillItem();
//        bi.getBill().getReferenceBill().getDeptId();
//        bi.getBill().getFromInstitution();
        if (searchKeyword.getRefBillNo() != null && !searchKeyword.getRefBillNo().toUpperCase().trim().equals("")) {
            sql += " and (upper(bi.bill.referenceBill.deptId) like :po) ";
            m.put("po", "%" + searchKeyword.getRefBillNo().toUpperCase().trim() + "%");
        }

        if (searchKeyword.getIns() != null) {
            sql += " and bi.bill.fromInstitution=:de ";
            m.put("de", searchKeyword.getIns());
        }

        m.put("bt", BillType.PharmacyGrnBill);
        m.put("fd", getFromDate());
        m.put("td", getToDate());

        billItems = getBillItemFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);
    }

    private double getSaleValueByDepartmentByBill(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = " select sum(i.netTotal) "
                + " from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false"
                + " and i.billType=:btp "
                + " and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";

        sql += "  order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartmentByBillItem(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = " select sum(i.netValue) "
                + " from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.bill.retired=false"
                //                + " and i.retired=false  "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)=:cl "
                + " and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += "  order by i.bill.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Object[]> fetchSaleValueByDepartment() {
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select FUNC('Date',i.createdAt),"
                + " i.bill.billClassType,"
                + " sum(i.netValue)"
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " group by FUNC('Date',i.createdAt),i.bill.billClassType"
                + " order by i.createdAt,i.bill.billClassType ";
        return getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

    }

    public void createSaleBillItems() {
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select i "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        if (item != null) {
            sql += " and i.item=:itm";
            m.put("itm", item);
        }

        sql += "  order by i.item.name,i.createdAt,i.bill.billClassType ";
        billItems = billItemFacade.findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Object[]> fetchSaleValueByPaymentmethod() {
        String sql;

        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select FUNC('Date',i.createdAt),"
                + " i.bill.paymentMethod,"
                + " sum(i.netValue)"
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " group by FUNC('Date',i.createdAt),i.bill.paymentMethod"
                + " order by i.createdAt,i.bill.paymentMethod ";
        return getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Object[]> fetchSaleValueByPaymentmethodByBill() {
        String sql;

        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select FUNC('Date',i.createdAt),"
                + " i.paymentMethod,"
                + " sum(i.netTotal)"
                + " from Bill i "
                + "where i.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + "and type(i)!=:cl "
                + "and i.createdAt between :fd and :td ";

        sql += " group by FUNC('Date',i.createdAt),i.paymentMethod"
                + " order by i.createdAt,i.paymentMethod ";
        return getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

    }

    private double getSaleValueByDepartment(Date date) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
//        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.bill.netTotal) "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                //                + "and type(i.bill)=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }
//
//        sql += "  order by i.bill.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Object[]> getSaleValueByDepartmentCategoryWise() {

        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
//        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i.item.category,sum(i.netValue) "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                //                + "and type(i.bill)=:cl "
                + " and i.bill.createdAt between :fd and :td "
                + " group by i.item.category.name"
                + " order by i.item.category.name ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }
//
//        sql += "  order by i.bill.deptId ";
        List<Object[]> saleValue = getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getTransIssueValueByDate(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacyTransferIssue);
        m.put("tde", getToDepartment());
        sql = "select sum(i.netTotal) "
                + "from Bill i "
                + " where i.department=:d "
                + " and i.retired=false "
                + " and i.toDepartment=:tde"
                + " and i.billType=:btp"
                + "  and type(i)=:cl"
                + " and i.createdAt between :fd and :td "
                + " order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartmentPaymentScheme(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal)"
                + "  from Bill i"
                + "  where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        sql += " order by i.deptId ";

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartmentPaymentSchemeP(Bill bill, PaymentScheme ps) {

        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) "
                + " from Bill i where "
                + " i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + " and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (ps != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", ps);

        }

        sql += " order by i.deptId ";

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getIssueValueByDepartment(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacyBhtPre);
        sql = "select sum(i.netTotal) "
                + " from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + " and type(i)=:cl "
                + " and i.createdAt between :fd and :td order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double calBillFee(Date date, FeeType fTy, BillType[] btyps) {
        String sql;
        Map m = new HashMap();
        sql = "select sum(f.feeGrossValue) "
                + " from BillFee f "
                + " where f.bill.retired=false "
                + " and f.bill.billType in :btyps "
                + " and f.bill.createdAt between :fd and :td ";
        if (institution != null) {
            sql += " and f.bill.toInstitution=:ins ";
            m.put("ins", institution);
        }
        if (department != null) {
            sql += " and f.bill.toDepartment=:dep ";
            m.put("dep", department);
        }
        sql += " and f.fee.feeType=:ft";
        Date fd = CommonFunctions.getStartOfDay(date);
        Date td = CommonFunctions.getEndOfDay(date);

        m.put("fd", fd);
        m.put("td", td);
        List<BillType> lbs = Arrays.asList(btyps);
        m.put("btyps", lbs);

        m.put("ft", fTy);
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
    }

    private double calBillFee(Date date, FeeType fTy, BillType btyp) {
        String sql;
        Map m = new HashMap();
        sql = "select sum(f.feeGrossValue) "
                + " from BillFee f "
                + " where f.bill.retired=false "
                + " and f.bill.billType =:btyp "
                + " and f.bill.createdAt between :fd and :td ";
        if (institution != null) {
            sql += " and f.bill.toInstitution=:ins ";
            m.put("ins", institution);
        }
        if (department != null) {
            sql += " and f.bill.toDepartment=:dep ";
            m.put("dep", department);
        }
        sql += " and f.fee.feeType=:ft";
        Date fd = CommonFunctions.getStartOfDay(date);
        Date td = CommonFunctions.getEndOfDay(date);

        m.put("fd", fd);
        m.put("td", td);
        List<BillType> lbs = Arrays.asList(btyp);
        m.put("btyp", lbs);

        m.put("ft", fTy);
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
    }

    private double calBillFee(Date date, FeeType fTy) {
        String sql;
        Map m = new HashMap();
        sql = "select sum(f.feeValue) "
                + " from BillFee f "
                + " where f.bill.retired=false "
                + " and f.bill.billType = :billType "
                + " and (f.bill.paymentMethod = :pm1 "
                + " or f.bill.paymentMethod = :pm2 "
                + " or f.bill.paymentMethod = :pm3 "
                + " or f.bill.paymentMethod = :pm4) "
                + " and f.bill.createdAt between :fd and :td ";
        if (institution != null) {
            sql += " and f.bill.toInstitution=:ins ";
            m.put("ins", institution);
        }
        if (department != null) {
            sql += " and f.bill.toDepartment=:dep ";
            m.put("dep", department);
        }
        sql += " and f.fee.feeType=:ft";
        Date fd = CommonFunctions.getStartOfDay(date);
        Date td = CommonFunctions.getEndOfDay(date);

        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);

        m.put("ft", fTy);
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
        return saleValue;
    }

    @Inject
    private SessionController sessionController;

    private double getHandOverValue(Date date) {

        String sql;
//
//        sql = "select abs(sum(f.total))"
//                + " from Bill f "
//                + " where f.retired=false "
//                + " and f.billType = :billType "
//                + " and type(f) = :class "
//                + " and f.createdAt between :fd and :td "
//                + " and( f.paymentMethod=:pm1"
//                + " or f.paymentMethod=:pm2"
//                + " or f.paymentMethod=:pm3"
//                + " or f.paymentMethod=:pm4 )"
//                + " and f.toInstitution=:ins "
//                + " and f.institution=:billedIns ";
//        
        sql = "select sum(f.total - f.staffFee) "
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and (f.paymentMethod = :pm1 "
                + " or f.paymentMethod = :pm2 "
                + " or f.paymentMethod = :pm3 "
                + " or f.paymentMethod = :pm4) "
                + " and f.createdAt between :fd and :td "
                + " and f.toInstitution=:ins ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        System.err.println("From " + fd);
        System.err.println("To " + td);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        //    m.put("ins", getSessionController().getInstitution());
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getHandOverDiscountValue(Date date) {

        String sql;

        sql = "select sum(f.discount)"
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and f.createdAt between :fd and :td "
                + " and( f.paymentMethod=:pm1"
                + " or f.paymentMethod=:pm2"
                + " or f.paymentMethod=:pm3"
                + " or f.paymentMethod=:pm4 )"
                + " and f.toInstitution=:ins ";
        //   + " and f.institution=:billedIns ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        // m.put("billedIns", getSessionController().getInstitution());

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getHandOverProfValue(Date date) {

        String sql;

        sql = "select sum(f.staffFee)"
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and f.createdAt between :fd and :td "
                + " and( f.paymentMethod=:pm1"
                + " or f.paymentMethod=:pm2"
                + " or f.paymentMethod=:pm3"
                + " or f.paymentMethod=:pm4 )"
                + " and f.toInstitution=:ins ";
        //      + " and f.institution=:billedIns ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        // m.put("billedIns", getSessionController().getInstitution());

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartmentByBillItem(Date date, PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm", paymentMethod);
        m.put("class", bill.getClass());
        //  m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue)"
                + "  from BillItem i "
                + " where i.bill.paymentMethod=:pm "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and  i.bill.referenceBill.department=:d "
                + " and type(i.bill)=:class "
                + " and i.bill.billType=:btp"
                + " and i.bill.createdAt between :fd and :td"
                + "  order by i.bill.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
        //   //System.err.println("from " + fromDate);
        //  //System.err.println("Sale Value " + saleValue);
        return saleValue;

    }

    private double getSaleValueByDepartmentByBill(Date date, PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm", paymentMethod);
        m.put("class", bill.getClass());
        //  m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal)"
                + "  from Bill i "
                + " where i.paymentMethod=:pm "
                + " and i.retired=false "
                + " and  i.referenceBill.department=:d "
                + " and type(i)=:class "
                + " and i.billType=:btp"
                + " and i.createdAt between :fd and :td"
                + "  order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
        //   //System.err.println("from " + fromDate);
        //  //System.err.println("Sale Value " + saleValue);
        return saleValue;

    }

//    private double getSaleValuePaymentmethod(Date date, PaymentMethod paymentMethod, Bill bill) {
//        //   List<Stock> billedSummery;
//        Date fd = getCommonFunctions().getStartOfDay(date);
//        Date td = getCommonFunctions().getEndOfDay(date);
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fd", fd);
//        m.put("td", td);
//        m.put("pm", paymentMethod);
//        m.put("class", bill.getClass());
//        m.put("btp", BillType.PharmacySale);
//        sql = "select sum(i.netTotal) "
//                + " from Bill i where type(i)=:class and i.paymentMethod=:pm and "
//                + " i.referenceBill.department=:d and i.billType=:btp and i.createdAt between :fd and :td ";
//        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//        return saleValue;
//
//    }
//
//    private double getDiscountValueByDepartment(Date date) {
//        //   List<Stock> billedSummery;
//        Date fd = getCommonFunctions().getStartOfDay(date);
//        Date td = getCommonFunctions().getEndOfDay(date);
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fd", fd);
//        m.put("td", td);
//        m.put("btp", BillType.PharmacyPre);
//        m.put("refType", BillType.PharmacySale);
//        sql = "select sum(i.discount) from Bill i where i.department=:d and i.referenceBill.billType=:refType "
//                + " and i.billType=:btp and i.createdAt between :fd and :td ";
//        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//        //System.err.println("from " + fromDate);
//        //System.err.println("Sale Value " + saleValue);
//        return saleValue;
//
//    }
    private double getDiscountValueByDepartmentByBill(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.discount)"
                + " from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getDiscountValueByDepartmentByBillItem(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.discount)"
                + " from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp"
                + "  and type(i.bill)=:cl "
                + " and i.bill.createdAt between :fd and :td ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getDiscountValueByDepartmentPaymentScheme(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.discount) "
                + " from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + " and type(i)=:cl"
                + "  and i.createdAt between :fd and :td ";
        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Bill> getSaleBillByDepartment(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + " and type(i)=:class and "
                + " i.createdAt between :fd and :td "
                + " order by i.deptId ";

        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<BillItem> getSaleBillItemByDepartment(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)=:class "
                + " and i.bill.createdAt between :fd and :td "
                + " order by i.bill.deptId ";

        return getBillItemFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Bill> getSaleBillByDepartmentPaymentScheme(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i from Bill i "
                + " where i.referenceBill.department=:d  "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)=:class "
                + " and i.createdAt between :fd and :td ";
        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        sql += " order by i.deptId ";

        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Bill> getIssueBillByDepartment(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacyBhtPre);
        sql = "select i from Bill i "
                + " where i.referenceBill.department=:d  "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)=:class"
                + "  and i.createdAt between :fd and :td "
                + " order by i.deptId ";
        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartment() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);

        sql = "select sum(i.netValue) from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.bill.retired=false"
                //                + " and i.retired=false  "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)!=:cl ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " and i.bill.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalIssue() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacyTransferIssue);

        sql = "select sum(i.netTotal)"
                + "  from Bill i where i.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)!=:cl "
                + " and i.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartmentPaymentScheme() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);

        sql = "select sum(i.netTotal)"
                + "  from Bill i"
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + "and type(i)!=:cl"
                + " and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calIssueGrantNetTotalByDepartment() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacyPre);

        sql = "select sum(i.netTotal)"
                + " from Bill i "
                + "where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + " and type(i)!=:cl "
                + " and i.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalIssue(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("tde", getToDepartment());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacyTransferIssue);
        sql = "select sum(i.netTotal)"
                + " from Bill i "
                + " where i.department=:d "
                + " and i.retired=false "
                + " and i.toDepartment=:tde "
                + " and i.billType=:btp "
                + " and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartment(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue)"
                + " from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.bill.retired=false "
                //                + " and i.retired=false  "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)=:class ";

        if (category != null) {
            sql += " and i.item.category=:cat ";
            m.put("cat", category);
        }

        sql += " and i.bill.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

//    private double calGrantNetTotalByDepartment() {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
////        m.put("class", bill.getClass());
//        // m.put("btp", BillType.PharmacyPre);
//        m.put("btp", BillType.PharmacySale);
//        sql = "select sum(i.bill.netTotal)"
//                + " from BillItem i "
//                + " where i.bill.referenceBill.department=:d "
//                + " and i.bill.billType=:btp ";
////                + " and type(i.bill)=:class ";
//
//        if (category != null) {
//            sql += " and i.item.category=:cat ";
//            m.put("cat", category);
//        }
//
//        sql += " and i.bill.createdAt between :fromDate and :toDate ";
//        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
    private double calGrantNetTotalByDepartmentPaymentScheme(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) "
                + " from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + " and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calIssueGrantNetTotalByDepartment(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacyPre);
        sql = "select sum(i.netTotal)"
                + "  from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp"
                + "  and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

//    private double calGrantHandOverNetotal(Bill bill) {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("ins", getInstitution());
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
//        m.put("class", bill.getClass());
//        m.put("pm1", PaymentMethod.Cash);
//        m.put("pm2", PaymentMethod.Card);
//        m.put("pm3", PaymentMethod.Cheque);
//        m.put("pm4", PaymentMethod.Slip);
//        m.put("btp", BillType.OpdBill);
//        sql = "select sum(abs(i.total) - (abs(i.staffFee) + abs(i.discount))) from "
//                + " Bill i where i.toInstitution=:ins "
//                + " and i.billType=:btp"
//                + " and type(i)=:class "
//                + " and( i.paymentMethod=:pm1 "
//                + " or i.paymentMethod=:pm2 "
//                + " or i.paymentMethod=:pm3 "
//                + " or i.paymentMethod=:pm4 )"
//                + " and i.createdAt between :fromDate and :toDate ";
//        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
//    private double calGrantHandOverNetotal() {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("ins", getInstitution());
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
//        m.put("pm1", PaymentMethod.Cash);
//        m.put("pm2", PaymentMethod.Card);
//        m.put("pm3", PaymentMethod.Cheque);
//        m.put("pm4", PaymentMethod.Slip);
//        m.put("btp", BillType.OpdBill);
//        sql = "select sum(abs(i.netTotal) - abs(i.staffFee))  "
//                + " from Bill i where "
//                + " i.toInstitution=:ins "
//                + " and i.billType=:btp "
//                + " and (i.paymentMethod=:pm1 "
//                + " or i.paymentMethod=:pm2 "
//                + " or i.paymentMethod=:pm3 "
//                + " or i.paymentMethod=:pm4 )"
//                + " and i.createdAt between :fromDate and :toDate ";
//        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
    private double calGrantHandOverTotal() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.total)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverProf() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.staffFee)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverDiscount() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.discount)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantTotalByPaymentMethodByBill(PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal)"
                + "  from Bill i"
                + "  where type(i)=:class"
                + " and i.retired=false "
                + "  and i.paymentMethod=:pm "
                + " and  i.referenceBill.department=:d"
                + " and i.billType=:btp "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantTotalByPaymentMethodByBillItem(PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue)"
                + "  from BillItem i"
                + "  where type(i.bill)=:class"
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + "  and i.bill.paymentMethod=:pm "
                + " and  i.bill.referenceBill.department=:d"
                + " and i.bill.billType=:btp "
                + " and i.bill.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

//    private double calGrantTotalByPaymentMethod(PaymentMethod paymentMethod) {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("pm", paymentMethod);
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
//        m.put("btp", BillType.PharmacySale);
//        sql = "select sum(i.netTotal)"
//                + " from Bill i "
//                + " where i.paymentMethod=:pm"
//                + "  and i.referenceBill.department=:d "
//                + " and i.billType=:btp "
//                + " and i.createdAt between :fromDate and :toDate ";
//        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
    private double calGrantTotalByPaymentMethodByBillItem(PaymentMethod paymentMethod) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue) "
                + " from BillItem i "
                + " where type(i.bill)!=:class "
                //                + " and i.retired=false "
                + " and i.bill.retired=false "
                + " and i.bill.paymentMethod=:pm "
                + " and i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                + " and i.bill.createdAt between :fromDate and :toDate ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantTotalByPaymentMethodByBill(PaymentMethod paymentMethod) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) "
                + " from Bill i "
                + " where type(i)!=:class "
                + " and i.retired=false "
                + " and i.paymentMethod=:pm "
                + " and i.referenceBill.department=:d "
                + " and i.billType=:btp "
                + " and i.createdAt between :fromDate and :toDate ";

        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentByBill(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", bill.getClass());
        sql = "select sum(i.discount) from Bill i "
                + " where type(i)=:class "
                + " and i.retired=false  "
                + " and i.referenceBill.department=:d "
                + " and  i.billType=:btp"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentByBillItem(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", bill.getClass());
        sql = "select sum(i.discount) from BillItem i "
                + " where type(i.bill)=:class "
                + " and i.retired=false  "
                + " and i.bill.retired=false  "
                + " and i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp"
                + " and i.bill.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentPaymentScheme(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", bill.getClass());
        sql = "select sum(i.discount) "
                + " from Bill i "
                + " where type(i)=:class "
                + " and i.retired=false "
                + " and i.referenceBill.department=:d "
                + " and i.billType=:btp "
                + " and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentByBill() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", PreBill.class);
        sql = "select sum(i.discount) from Bill i "
                + " where type(i)!=:class "
                + " and i.referenceBill.department=:d "
                + " and i.retired=false  "
                + " and i.billType=:btp "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentByBillItem() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", PreBill.class);
        sql = "select sum(i.discount)"
                + "  from BillItem i "
                + " where type(i.bill)!=:class "
                + " and i.bill.referenceBill.department=:d "
                //                + " and i.retired=false  "
                + " and i.bill.retired=false  "
                + " and i.bill.billType=:btp "
                + " and i.bill.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentPaymentScheme() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", PreBill.class);
        sql = "select sum(i.discount) "
                + " from Bill i "
                + " where type(i)!=:class "
                + " and i.retired=false "
                + " and i.referenceBill.department=:d "
                + " and  i.billType=:btp "
                + " and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    public void createSaleReportByDate() {
        billedSummery = new PharmacySummery();

        List<Object[]> list = fetchSaleValueByDepartment();
        TreeMap<Date, String1Value3> hm = new TreeMap<>();

        for (Object[] obj : list) {
            Date date = (Date) obj[0];
            BillClassType billClassType = (BillClassType) obj[1];
            Double value = (Double) obj[2];

            String1Value3 newRow = (String1Value3) hm.get(date);

            if (newRow == null) {
                newRow = new String1Value3();
                newRow.setDate(date);
            } else {
                hm.remove(date);
            }

            switch (billClassType) {
                case BilledBill:
                    newRow.setValue1(value);
                    break;
                case CancelledBill:
                    newRow.setValue2(value);
                    break;
                case RefundBill:
                    newRow.setValue3(value);
                    break;
            }

            hm.put(date, newRow);

        }

//        Collections.s
        List<String1Value3> listRow = new ArrayList<>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            listRow.add((String1Value3) pairs.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.err.println(listRow);

        billedSummery.setBills(listRow);

        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();

    }
//    public void createSaleReportByDate() {
//        billedSummery = new PharmacySummery();
//
//        billedSummery.setBills(new ArrayList<String1Value3>());
//
//        Date nowDate = getFromDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(nowDate);
//
//        while (nowDate.before(getToDate())) {
//
//            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//            String formattedDate = df.format(nowDate);
//
//            String1Value3 newRow = new String1Value3();
//            newRow.setString(formattedDate);
//            newRow.setValue1(getSaleValueByDepartment(nowDate, new BilledBill()));
//            newRow.setValue2(getSaleValueByDepartment(nowDate, new CancelledBill()));
//            newRow.setValue3(getSaleValueByDepartment(nowDate, new RefundBill()));
//
//            billedSummery.getBills().add(newRow);
//
//            Calendar nc = Calendar.getInstance();
//            nc.setTime(nowDate);
//            nc.add(Calendar.DATE, 1);
//            nowDate = nc.getTime();
//
//        }
//
//        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
//        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
//        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));
//
//        grantNetTotal = calGrantNetTotalByDepartment();
//
//    }

    public void createSaleReportByDate2() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);
            newRow.setValue1(getSaleValueByDepartment(nowDate));

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }
//
//        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
//        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
//        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();

    }

    List<String1Value3> string1Value3s;

    public List<String1Value3> getString1Value3s() {
        return string1Value3s;
    }

    List<CategoryMovementReportRow> categoryMovementReportRows;

    double totalOpdSale;
    double totalInwardIssue;
    double totalDepartmentIssue;
    double totalTatalValue;
    double totalPurchaseValue;
    double totalMargineValue;

    public void createCategoryMovementReport() {
        String jpql;
        Map m = new HashMap();
        PharmaceuticalBillItem pbi = new PharmaceuticalBillItem();
//        pbi.getBillItem().getBill().getCreatedAt();
//        pbi.getBillItem().getBill().getBillType();
//        pbi.getBillItem().getItem();
//        pbi.getPurchaseRate();
//
//        pbi.getBillItem().getBill().getDepartment();
//        pbi.getBillItem().getNetValue();
//        pbi.getItemBatch().getPurcahseRate();
//        pbi.getQty();

        m
                .put("bc", PreBill.class
                );
        m.put(
                "fd", fromDate);
        m.put(
                "td", toDate);
        m.put(
                "cat", category);
        jpql = "select pbi.billItem.bill.billType, pbi.billItem.item, sum(pbi.billItem.netValue), sum(pbi.itemBatch.purcahseRate*pbi.qty) "
                + " from PharmaceuticalBillItem pbi "
                + " where type(pbi.billItem.bill)=:bc "
                + " and pbi.billItem.bill.createdAt between :fd and :td "
                + " and pbi.billItem.item.category=:cat ";
        if (department
                != null) {
            jpql = jpql + " and pbi.billItem.bill.department=:dept ";
            m.put("dept", department);
        }
        jpql = jpql + " group by pbi.billItem.bill.billType, pbi.billItem.item ";
        jpql = jpql + " order by pbi.billItem.item.name ";
        List<Object[]> objs = getBillFacade().findAggregates(jpql, m, TemporalType.TIMESTAMP);
        categoryMovementReportRows = new ArrayList<>();
        Item pi = null;
        CategoryMovementReportRow r;
        r = new CategoryMovementReportRow();
        totalOpdSale = 0.0;
        totalInwardIssue = 0.0;
        totalDepartmentIssue = 0.0;
        totalPurchaseValue = 0.0;
        totalTatalValue = 0.0;
        totalMargineValue = 0.0;
        for (Object o[] : objs) {

            try {

                Item ti;
                BillType tbt;
                double sv;
                double cv;
                ti = (Item) o[1];

                tbt = (BillType) o[0];

                sv = (double) o[2];
                cv = (double) o[3];
                System.out.println("cv = " + cv);
                System.out.println("sv = " + sv);

                System.out.println("pi = " + pi);
                System.out.println("ti = " + ti);

                if (pi == null || !ti.equals(pi)) {
                    System.out.println("new item - " + ti.getName());
                    r = new CategoryMovementReportRow();
                    r.setItem(ti);
                    r.setDepartmentIssue(0.0);
                    r.setInwardIssue(0.0);
                    r.setMarginValue(0.0);
                    r.setOpdSale(0.0);
                    r.setPurchaseValue(0.0);
                    r.setTotal(0.0);
                    r.setTransfer(0.0);
                    r.setTransferIn(0.0);
                    r.setTransferOut(0.0);
                    pi = ti;
                    categoryMovementReportRows.add(r);
                    System.out.println("size = " + categoryMovementReportRows.size());
                }

                System.out.println("tbt = " + tbt);

                switch (tbt) {
                    case PharmacySale:
                    case PharmacyPre:
                        System.out.println("pharmacy sale");
                        System.out.println("r.getOpdSale() = " + r.getOpdSale());
                        r.setOpdSale(r.getOpdSale() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        break;
                    case PharmacyBhtPre:
                        System.out.println("bht sale ");
                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        r.setInwardIssue(r.getInwardIssue() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        break;
                    case PharmacyIssue:
                        System.out.println("pharmacy issue ");
                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        r.setDepartmentIssue(r.getDepartmentIssue() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        break;
                    case PharmacyTransferIssue:
                        System.out.println("tx issue ");
                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        r.setTransferIn(r.getTransferIn() + sv);
                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        break;
                    case PharmacyTransferReceive:
                        System.out.println("tx issue ");
                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        r.setTransferOut(r.getTransferOut() + sv);
                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        break;

                    default:
                        System.out.println("other bill type");
                }

            } catch (Exception e) {
                System.out.println("e = " + e);
            }

            r.setTotal(r.getOpdSale() + r.getInwardIssue() + r.getDepartmentIssue());
            System.out.println("r.getTotal() = " + r.getTotal());
            r.setMarginValue(r.getTotal() + r.getPurchaseValue());

            totalOpdSale += r.getOpdSale();
            totalInwardIssue += r.getInwardIssue();
            totalDepartmentIssue += r.getDepartmentIssue();
            totalPurchaseValue += r.getPurchaseValue();
            totalTatalValue += r.getTotal();
            totalMargineValue += r.getMarginValue();
        }
    }

    public void createCategoryMovementReportByItemBatch() {
        String jpql;
        Map m = new HashMap();

        m
                .put("bc", PreBill.class
                );
        m.put(
                "fd", fromDate);
        m.put(
                "td", toDate);

        jpql = "select pbi.billItem.bill.billType,"
                + " pbi.itemBatch, "
                + " sum(pbi.billItem.netValue), "
                + " sum(pbi.qty),"
                + " stk"
                + " from PharmaceuticalBillItem pbi,Stock stk "
                + " where type(pbi.billItem.bill)=:bc "
                + " and stk.itemBatch=pbi.itemBatch "
                + " and pbi.billItem.bill.createdAt between :fd and :td ";

        if (category
                != null) {
            jpql = jpql + " and pbi.billItem.item.category=:cat";
            m.put("cat", category);
        }

        if (department
                != null) {
            jpql = jpql + " and pbi.billItem.bill.department=:dept ";
            m.put("dept", department);
        }
        jpql = jpql + " group by pbi.billItem.bill.billType, pbi.itemBatch ";
        jpql = jpql + " order by pbi.itemBatch.item.name ";
        List<Object[]> objs = getBillFacade().findAggregates(jpql, m, TemporalType.TIMESTAMP);
        categoryMovementReportRows = new ArrayList<>();
        ItemBatch pi = null;
        CategoryMovementReportRow r;
        r = new CategoryMovementReportRow();
        totalOpdSale = 0.0;
        totalInwardIssue = 0.0;
        totalDepartmentIssue = 0.0;
        totalPurchaseValue = 0.0;
        totalTatalValue = 0.0;
        totalMargineValue = 0.0;
        for (Object o[] : objs) {

            try {

                ItemBatch itemBatch;
                BillType billType;
                Stock stock;
                double netValue;
                double qty;

                billType = (BillType) o[0];
                itemBatch = (ItemBatch) o[1];
                netValue = (double) o[2];
                qty = (double) o[3];
                stock = (Stock) o[4];

                if (pi == null || !itemBatch.equals(pi)) {
                    r = new CategoryMovementReportRow();
                    r.setItemBatch(itemBatch);
                    r.setStock(stock);
                    r.setDepartmentIssue(0.0);
                    r.setInwardIssue(0.0);
                    r.setMarginValue(0.0);
                    r.setOpdSale(0.0);
                    r.setPurchaseValue(0.0);
                    r.setTotal(0.0);
                    r.setTransfer(0.0);
                    r.setTransferIn(0.0);
                    r.setTransferOut(0.0);
                    pi = itemBatch;
                    categoryMovementReportRows.add(r);
//                    System.out.println("size = " + categoryMovementReportRows.size());
                }

//                System.out.println("tbt = " + tbt);
                switch (billType) {
                    case PharmacySale:
                    case PharmacyPre:
//                        System.out.println("pharmacy sale");
//                        System.out.println("r.getOpdSale() = " + r.getOpdSale());
                        r.setOpdSale(r.getOpdSale() + netValue);
                        r.setOpdSaleQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getOpdSaleQty()));
                        break;
                    case PharmacyBhtPre:
//                        System.out.println("bht sale ");
//                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        r.setInwardIssue(r.getInwardIssue() + netValue);
                        r.setInwardIssueQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getInwardIssueQty()));
//                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        break;
                    case PharmacyIssue:
//                        System.out.println("pharmacy issue ");
//                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        r.setDepartmentIssue(r.getDepartmentIssue() + netValue);
                        r.setDepartmentIssueQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getDepartmentIssueQty()));
//                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        break;
                    case PharmacyTransferIssue:
//                        System.out.println("tx issue ");
//                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        r.setTransferIn(r.getTransferIn() + netValue);
//                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        break;
                    case PharmacyTransferReceive:
//                        System.out.println("tx issue ");
//                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        r.setTransferOut(r.getTransferOut() + netValue);
//                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        break;

                    default:
                        System.out.println("other bill type");
                }

            } catch (Exception e) {
                System.out.println("e = " + e);
            }

            r.setTotal(r.getOpdSale() + r.getInwardIssue() + r.getDepartmentIssue());
            System.out.println("r.getTotal() = " + r.getTotal());
            r.setMarginValue(r.getTotal() + r.getPurchaseValue());

            totalOpdSale += r.getOpdSale();
            totalInwardIssue += r.getInwardIssue();
            totalDepartmentIssue += r.getDepartmentIssue();
            totalPurchaseValue += r.getStock().getStock() * r.getItemBatch().getPurcahseRate();
            totalTatalValue += r.getTotal();
            totalMargineValue += r.getMarginValue();
        }
    }

    public List<CategoryMovementReportRow> getCategoryMovementReportRows() {
        return categoryMovementReportRows;
    }

    public void setCategoryMovementReportRows(List<CategoryMovementReportRow> categoryMovementReportRows) {
        this.categoryMovementReportRows = categoryMovementReportRows;
    }

    public void createSaleReportByDate3() {

        List<Object[]> list = getSaleValueByDepartmentCategoryWise();
        string1Value3s = new ArrayList<>();

        for (Object[] obj : list) {
            String1Value3 string1Value3 = new String1Value3();
            string1Value3.setString(((Category) obj[0]).toString());
            string1Value3.setValue1((Double) obj[1]);
            string1Value3s.add(string1Value3);

        }
        grantNetTotal = calGrantNetTotalByDepartment();

    }

    public void createIssueReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);
            newRow.setValue1(getTransIssueValueByDate(nowDate, new BilledBill()));
            newRow.setValue2(getTransIssueValueByDate(nowDate, new CancelledBill()));
            newRow.setValue3(getTransIssueValueByDate(nowDate, new RefundBill()));

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(calGrantNetTotalIssue(new BilledBill()));
        billedSummery.setCancelledTotal(calGrantNetTotalIssue(new CancelledBill()));
        billedSummery.setRefundedTotal(calGrantNetTotalIssue(new RefundBill()));

        grantNetTotal = calGrantNetTotalIssue();

    }

    public void createLabHadnOverReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double handOverValue = getHandOverValue(nowDate);

            double discount = getHandOverDiscountValue(nowDate);

            double proTot = getHandOverProfValue(nowDate);

            newRow.setValue1(handOverValue + proTot);
            newRow.setValue2(discount);
            newRow.setValue3(proTot);

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(calGrantHandOverTotal());
        billedSummery.setCancelledTotal(calGrantHandOverDiscount());
        billedSummery.setRefundedTotal(calGrantHandOverProf());

    }

    public double calValueSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep"
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValueSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    //pasan
    public void createFeeSummeryWithCounts(BillType[] btps) {
        billedSummery = new PharmacySummery();
        billedSummery.setBills(new ArrayList<String1Value3>());
        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;
        long countTotal = 0l;
        double hospitalFee;
        double proTot;
        double regentFee;
        long count;

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            hospitalFee = calBillFee(nowDate, FeeType.OwnInstitution, btps);
            proTot = calBillFee(nowDate, FeeType.Staff, btps);
            regentFee = calBillFee(nowDate, FeeType.Chemical, btps);

            count = billReportBean.calulateRevenueBillItemCount(CommonFunctions.getStartOfDay(nowDate),
                    CommonFunctions.getEndOfDay(nowDate), null, institution, department, btps);
            countTotal += count;

            newRow.setValue1(hospitalFee);
            newRow.setValue2(regentFee);
            newRow.setValue3(proTot);
            newRow.setLongValue1(count);

            hospitalFeeTot += hospitalFee;
            profeTotal += proTot;
            regentTot += regentFee;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);
        billedSummery.setCount(countTotal);

    }

    public void createFeeSummeryWithoutCounts(BillType[] btps) {
        billedSummery = new PharmacySummery();
        billedSummery.setBills(new ArrayList<String1Value3>());
        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;
        double hospitalFee;
        double proTot;
        double regentFee;

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            hospitalFee = calBillFee(nowDate, FeeType.OwnInstitution, btps);
            proTot = calBillFee(nowDate, FeeType.Staff, btps);
            regentFee = calBillFee(nowDate, FeeType.Chemical, btps);

            newRow.setValue1(hospitalFee);
            newRow.setValue2(regentFee);
            newRow.setValue3(proTot);

            hospitalFeeTot += hospitalFee;
            profeTotal += proTot;
            regentTot += regentFee;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);

    }

    public void createDailyOpdFeeSummeryWithCounts() {
        BillType[] btps = new BillType[]{BillType.OpdBill, BillType.LabBill};
        createFeeSummeryWithCounts(btps);
    }

    public void createDailyOpdFeeSummeryWithoutCounts() {
        BillType[] btps = new BillType[]{BillType.OpdBill, BillType.LabBill};
        createFeeSummeryWithoutCounts(btps);
    }

    public void createDailyInwardFeeSummeryWithCounts() {
        BillType[] btps = new BillType[]{BillType.InwardBill};
        createFeeSummeryWithCounts(btps);
    }

    public void createDailyInwardFeeSummeryWithoutCounts() {
        BillType[] btps = new BillType[]{BillType.InwardBill};
        createFeeSummeryWithoutCounts(btps);
    }

    public void createLabSummeryInward() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;

        //double regentFee;
        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double hospitalFeeCash = calBillFee(nowDate, FeeType.OwnInstitution, BillType.InwardBill);

            double proTotCash = calBillFee(nowDate, FeeType.Staff, BillType.InwardBill);

            double regentFeeCash = calBillFee(nowDate, FeeType.Chemical, BillType.InwardBill);

//            //inward bills
//            double hospitaFeeInward = calBillFee(nowDate, FeeType.OwnInstitution, BillType.InwardBill);
//            //double 
            newRow.setValue1(hospitalFeeCash);
            newRow.setValue2(regentFeeCash);
            newRow.setValue3(proTotCash);

            hospitalFeeTot += hospitalFeeCash;
            profeTotal += proTotCash;
            regentTot += regentFeeCash;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);

    }

    List<Bill> getLabBills(BillType billType, Bill bill) {
        Map hm = new HashMap();
        String sql;
        System.out.println("In to method");
        sql = "SELECT b FROM Bill b "
                + " WHERE b.createdAt between :fromDate and :toDate "
                + " and type(b)=:bill "
                + " and b.retired=false "
                + " and b.billType=:btp "
                + " and b.institution=:ins "
                + " and b.department=:dep ";

        hm.put("btp", billType);
        hm.put("bill", bill.getClass());
        hm.put("fromDate", getFromDate());
        hm.put("toDate", getToDate());
        hm.put("ins", getInstitution());
        hm.put("dep", getDepartment());

        return getBillFacade().findBySQL(sql, hm, TemporalType.TIMESTAMP);

    }

    public double getLabBillTotal(BillType billType, Bill bill) {
        Map hm = new HashMap();
        String sql;

        sql = "SELECT sum(b.netTotal) FROM Bill b "
                + " WHERE b.createdAt between :fromDate and :toDate "
                + " and type(b)=:bill "
                + " and b.retired=false "
                + " and b.billType=:btp "
                + " and b.institution=:ins "
                + " and b.department=:dep ";

        hm.put("btp", billType);
        hm.put("bill", bill.getClass());
        hm.put("fromDate", getFromDate());
        hm.put("toDate", getToDate());
        hm.put("ins", getInstitution());
        hm.put("dep", getDepartment());

        return getBillFacade().findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);

    }

    public void createLabReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double hospitalFee = calBillFee(nowDate, FeeType.OwnInstitution);

            double proTot = calBillFee(nowDate, FeeType.Staff);

            double regentFee = calBillFee(nowDate, FeeType.Chemical);

            newRow.setValue1(hospitalFee);
            newRow.setValue2(regentFee);
            newRow.setValue3(proTot);

            hospitalFeeTot += hospitalFee;
            profeTotal += proTot;
            regentTot += regentFee;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);

    }

    public double calValue(BillType billType, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, PaymentMethod paymentMethod, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);

        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCountSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp"
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCountSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount(BillType billType, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    //Bht issue & Unit issue
    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.paymentMethod=:pm ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);

        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.paymentMethod=:pm ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }
    //
    @EJB
    DepartmentFacade departmentFacade;

    public List<Department> fetchDepartment(DepartmentType departmentType) {
        String sql = "Select d from Department d"
                + " where d.retired=false "
                + " and d.departmentType=:dtp ";
        HashMap hm = new HashMap();
        hm.put("dtp", departmentType);
        return departmentFacade.findBySQL(sql, hm);

    }

    public void createPharmacyReport() {
        List<Department> departments = fetchDepartment(DepartmentType.Pharmacy);

        saleValuesCash = new ArrayList<>();
        saleValuesCheque = new ArrayList<>();
        saleValuesSlip = new ArrayList<>();
        saleValuesCard = new ArrayList<>();
        saleValuesCredit = new ArrayList<>();
        wholeSaleValuesCash = new ArrayList<>();
        wholeSaleValuesCheque = new ArrayList<>();
        wholeSaleValuesSlip = new ArrayList<>();
        wholeSaleValuesCard = new ArrayList<>();
        wholeSaleValuesCredit = new ArrayList<>();
        bhtIssues = new ArrayList<>();
        unitIssues = new ArrayList<>();

        totalPSCashBV = 0.0;
        totalPSCashCV = 0.0;
        totalPSCashRV = 0.0;
        totalPSCashNV = 0.0;
        totalPSCashBC = 0.0;
        totalPSCashRC = 0.0;
        totalPSCashNC = 0.0;

        totalPSCreditBV = 0.0;
        totalPSCreditCV = 0.0;
        totalPSCreditRV = 0.0;
        totalPSCreditNV = 0.0;
        totalPSCreditBC = 0.0;
        totalPSCreditRC = 0.0;
        totalPSCreditNC = 0.0;

        totalPSCardBV = 0.0;
        totalPSCardCV = 0.0;
        totalPSCardRV = 0.0;
        totalPSCardNV = 0.0;
        totalPSCardBC = 0.0;
        totalPSCardRC = 0.0;
        totalPSCardNC = 0.0;

        totalPSSlipBV = 0.0;
        totalPSSlipCV = 0.0;
        totalPSSlipRV = 0.0;
        totalPSSlipNV = 0.0;
        totalPSSlipBC = 0.0;
        totalPSSlipRC = 0.0;
        totalPSSlipNC = 0.0;

        totalPSChequeBV = 0.0;
        totalPSChequeCV = 0.0;
        totalPSChequeRV = 0.0;
        totalPSChequeNV = 0.0;
        totalPSChequeBC = 0.0;
        totalPSChequeRC = 0.0;
        totalPSChequeNC = 0.0;

        totalPWSCashBV = 0.0;
        totalPWSCashCV = 0.0;
        totalPWSCashRV = 0.0;
        totalPWSCashNV = 0.0;
        totalPWSCashBC = 0.0;
        totalPWSCashRC = 0.0;
        totalPWSCashNC = 0.0;

        totalPWSCreditBV = 0.0;
        totalPWSCreditCV = 0.0;
        totalPWSCreditRV = 0.0;
        totalPWSCreditNV = 0.0;
        totalPWSCreditBC = 0.0;
        totalPWSCreditRC = 0.0;
        totalPWSCreditNC = 0.0;

        totalPWSCardBV = 0.0;
        totalPWSCardCV = 0.0;
        totalPWSCardRV = 0.0;
        totalPWSCardNV = 0.0;
        totalPWSCardBC = 0.0;
        totalPWSCardRC = 0.0;
        totalPWSCardNC = 0.0;

        totalPWSSlipBV = 0.0;
        totalPWSSlipCV = 0.0;
        totalPWSSlipRV = 0.0;
        totalPWSSlipNV = 0.0;
        totalPWSSlipBC = 0.0;
        totalPWSSlipRC = 0.0;
        totalPWSSlipNC = 0.0;

        totalPWSChequeBV = 0.0;
        totalPWSChequeCV = 0.0;
        totalPWSChequeRV = 0.0;
        totalPWSChequeNV = 0.0;
        totalPWSChequeBC = 0.0;
        totalPWSChequeRC = 0.0;
        totalPWSChequeNC = 0.0;

        totalBHTIssueBV = 0.0;
        totalBHTIssueCV = 0.0;
        totalBHTIssueRV = 0.0;
        totalBHTIssueNV = 0.0;
        totalBHTIssueBC = 0.0;
        totalBHTIssueRC = 0.0;
        totalBHTIssueNC = 0.0;

        totalUnitIssueBV = 0.0;
        totalUnitIssueCV = 0.0;
        totalUnitIssueRV = 0.0;
        totalUnitIssueNV = 0.0;
        totalUnitIssueBC = 0.0;
        totalUnitIssueRC = 0.0;
        totalUnitIssueNC = 0.0;

        for (Department dep : departments) {
            String1Value6 newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPSCashBV += newRow.getValue1();
            totalPSCashRV += newRow.getValue2();
            totalPSCashNV += newRow.getValue3();
            totalPSCashBC += newRow.getValue4();
            totalPSCashRC += newRow.getValue5();
            totalPSCashNC += newRow.getValue6();
            totalPSCashCV += newRow.getValue7();
            saleValuesCash.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPSChequeBV += newRow.getValue1();
            totalPSChequeRV += newRow.getValue2();
            totalPSChequeNV += newRow.getValue3();
            totalPSChequeBC += newRow.getValue4();
            totalPSChequeRC += newRow.getValue5();
            totalPSChequeNC += newRow.getValue6();
            totalPSChequeCV += newRow.getValue7();
            saleValuesCheque.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPSSlipBV += newRow.getValue1();
            totalPSSlipCV += newRow.getValue7();
            totalPSSlipRV += newRow.getValue2();
            totalPSSlipNV += newRow.getValue3();
            totalPSSlipBC += newRow.getValue4();
            totalPSSlipRC += newRow.getValue5();
            totalPSSlipNC += newRow.getValue6();
            saleValuesSlip.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacySale, PaymentMethod.Card, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPSCardBV += newRow.getValue1();
            totalPSCardCV += newRow.getValue7();
            totalPSCardRV += newRow.getValue2();
            totalPSCardNV += newRow.getValue3();
            totalPSCardBC += newRow.getValue4();
            totalPSCardRC += newRow.getValue5();
            totalPSCardNC += newRow.getValue6();
            saleValuesCard.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill()));
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPSCreditBV += newRow.getValue1();
            totalPSCreditCV += newRow.getValue7();
            totalPSCreditRV += newRow.getValue2();
            totalPSCreditNV += newRow.getValue3();
            totalPSCreditBC += newRow.getValue4();
            totalPSCreditRC += newRow.getValue5();
            totalPSCreditNC += newRow.getValue6();
            saleValuesCredit.add(newRow);

            /////////Pharmacy whole sale
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cash, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cash, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPWSCashBV += newRow.getValue1();
            totalPWSCashRV += newRow.getValue2();
            totalPWSCashNV += newRow.getValue3();
            totalPWSCashBC += newRow.getValue4();
            totalPWSCashRC += newRow.getValue5();
            totalPWSCashNC += newRow.getValue6();
            totalPWSCashCV += newRow.getValue7();
            wholeSaleValuesCash.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cheque, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cheque, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPWSChequeBV += newRow.getValue1();
            totalPWSChequeRV += newRow.getValue2();
            totalPWSChequeNV += newRow.getValue3();
            totalPWSChequeBC += newRow.getValue4();
            totalPWSChequeRC += newRow.getValue5();
            totalPWSChequeNC += newRow.getValue6();
            totalPWSChequeCV += newRow.getValue7();
            wholeSaleValuesCheque.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Slip, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Slip, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPWSSlipBV += newRow.getValue1();
            totalPWSSlipCV += newRow.getValue7();
            totalPWSSlipRV += newRow.getValue2();
            totalPWSSlipNV += newRow.getValue3();
            totalPWSSlipBC += newRow.getValue4();
            totalPWSSlipRC += newRow.getValue5();
            totalPWSSlipNC += newRow.getValue6();
            wholeSaleValuesSlip.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Card, dep, new BilledBill()));
            newRow.setValue7(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Card, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPWSCardBV += newRow.getValue1();
            totalPWSCardCV += newRow.getValue7();
            totalPWSCardRV += newRow.getValue2();
            totalPWSCardNV += newRow.getValue3();
            totalPWSCardBC += newRow.getValue4();
            totalPWSCardRC += newRow.getValue5();
            totalPWSCardNC += newRow.getValue6();
            wholeSaleValuesCard.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacyWholeSale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Credit, dep, new BilledBill()));
            newRow.setValue4(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Credit, dep, new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacyWholeSale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalPWSCreditBV += newRow.getValue1();
            totalPWSCreditCV += newRow.getValue7();
            totalPWSCreditRV += newRow.getValue2();
            totalPWSCreditNV += newRow.getValue3();
            totalPWSCreditBC += newRow.getValue4();
            totalPWSCreditRC += newRow.getValue5();
            totalPWSCreditNC += newRow.getValue6();
            wholeSaleValuesCredit.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValue2(BillType.PharmacyBhtPre, dep, new PreBill(), new CancelledBill()));
            newRow.setValue2(calValue2(BillType.PharmacyBhtPre, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCount2(BillType.PharmacyBhtPre, dep, new PreBill()));
            newRow.setValue7(calCount2(BillType.PharmacyBhtPre, dep, new CancelledBill()));
            newRow.setValue5(calCount2(BillType.PharmacyBhtPre, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalBHTIssueBV += newRow.getValue1();
            totalBHTIssueCV += newRow.getValue7();
            totalBHTIssueRV += newRow.getValue2();
            totalBHTIssueNV += newRow.getValue3();
            totalBHTIssueBC += newRow.getValue4();
            totalBHTIssueRC += newRow.getValue5();
            totalBHTIssueNC += newRow.getValue6();
            bhtIssues.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValue2(BillType.PharmacyIssue, dep, new PreBill(), new CancelledBill()));
            newRow.setValue2(calValue2(BillType.PharmacyIssue, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCount2(BillType.PharmacyIssue, dep, new PreBill()));
            newRow.setValue7(calCount2(BillType.PharmacyIssue, dep, new CancelledBill()));
            newRow.setValue5(calCount2(BillType.PharmacyIssue, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5() - newRow.getValue7());
            totalUnitIssueBV += newRow.getValue1();
            totalUnitIssueCV += newRow.getValue7();
            totalUnitIssueRV += newRow.getValue2();
            totalUnitIssueNV += newRow.getValue3();
            totalUnitIssueBC += newRow.getValue4();
            totalUnitIssueRC += newRow.getValue5();
            totalUnitIssueNC += newRow.getValue6();
            unitIssues.add(newRow);
        }

    }

    public void createSalePaymentMethod() {
        billedPaymentSummery = new PharmacyPaymetMethodSummery();

        List<Object[]> list = fetchSaleValueByPaymentmethod();
        TreeMap<Date, String2Value4> hm = new TreeMap<>();

        for (Object[] obj : list) {
            Date date = (Date) obj[0];
            PaymentMethod pm = (PaymentMethod) obj[1];
            Double value = (Double) obj[2];

            String2Value4 newRow = (String2Value4) hm.get(date);

            if (newRow == null) {
                newRow = new String2Value4();
                newRow.setDate(date);
            } else {
                hm.remove(date);
            }

            switch (pm) {
                case Cash:
                    newRow.setValue1(value);
                    break;
                case Credit:
                    newRow.setValue2(value);
                    break;
                case Card:
                    newRow.setValue3(value);
                    break;
            }

            hm.put(date, newRow);

        }

        List<String2Value4> listRow = new ArrayList<>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            listRow.add((String2Value4) pairs.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.err.println(listRow);

        billedPaymentSummery.setBills(listRow);

        billedPaymentSummery.setCashTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash));

        ////////////
        billedPaymentSummery.setCreditTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit));

        ////////////////
        billedPaymentSummery.setCardTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card));

        grantCardTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card);
        grantCashTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash);
        grantCreditTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit);

    }

    public void createSalePaymentMethodByBill() {
        billedPaymentSummery = new PharmacyPaymetMethodSummery();

        List<Object[]> list = fetchSaleValueByPaymentmethodByBill();
        TreeMap<Date, String2Value4> hm = new TreeMap<>();

        for (Object[] obj : list) {
            Date date = (Date) obj[0];
            PaymentMethod pm = (PaymentMethod) obj[1];
            Double value = (Double) obj[2];

            String2Value4 newRow = (String2Value4) hm.get(date);

            if (newRow == null) {
                newRow = new String2Value4();
                newRow.setDate(date);
            } else {
                hm.remove(date);
            }

            switch (pm) {
                case Cash:
                    newRow.setValue1(value);
                    break;
                case Credit:
                    newRow.setValue2(value);
                    break;
                case Card:
                    newRow.setValue3(value);
                    break;
            }

            hm.put(date, newRow);

        }

        List<String2Value4> listRow = new ArrayList<>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            listRow.add((String2Value4) pairs.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.err.println(listRow);

        billedPaymentSummery.setBills(listRow);

        billedPaymentSummery.setCashTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash));

        ////////////
        billedPaymentSummery.setCreditTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit));

        ////////////////
        billedPaymentSummery.setCardTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card));

        grantCardTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card);
        grantCashTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash);
        grantCreditTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit);

    }

    @EJB
    ItemBatchFacade itemBatchFacade;

    List<ItemWithDepBHTIssue> itemWithDepBHTIssues;

    double totalBHTIssue;

    public List<ItemWithDepBHTIssue> getItemWithDepBHTIssues() {
        return itemWithDepBHTIssues;
    }

    public void setItemWithDepBHTIssues(List<ItemWithDepBHTIssue> itemWithDepBHTIssues) {
        this.itemWithDepBHTIssues = itemWithDepBHTIssues;
    }

    public double getTotalBHTIssue() {
        return totalBHTIssue;
    }

    public void setTotalBHTIssue(double totalBHTIssue) {
        this.totalBHTIssue = totalBHTIssue;
    }

    public void createPharmacyIssueBHT() {
        itemWithDepBHTIssues = new ArrayList<>();
        ItemWithDepBHTIssue depBHTIssue = null;
        totalBHTIssue = 0.0;
        for (ItemBatch i : fetchPharmacyItemBatchs(new PreBill())) {
            List<String1Value3> s1v3s = new ArrayList<>();
            double total = 0.0;
            for (Department d : fetchDepartments()) {
                for (Object[] obj : fetchPharmacyItemTotals(d, i, new PreBill())) {
                    String1Value3 s1v3 = new String1Value3();
                    double qty = 0.0;
                    double magine = 0.0;
                    double vaue = 0.0;
                    if (obj[0] != null) {
                        qty = (double) obj[0];
                        magine = (double) obj[1];
                        vaue = (double) obj[2];
                    }

                    if (qty != 0) {
                        System.out.println("i.getName() = " + i.getItem().getName());
                        System.out.println("d.getName() = " + d.getName());
                        s1v3.setString(d.getName());
                        s1v3.setValue1(qty);
                        s1v3.setValue2(magine);
                        s1v3.setValue3(vaue);
                        System.out.println("total = " + total);
                        System.out.println("s1v3.getValue3() = " + s1v3.getValue3());
                        total += s1v3.getValue3();
                        System.out.println("total = " + total);
                        s1v3s.add(s1v3);
                    }

                }
            }
            if (s1v3s != null) {
                depBHTIssue = new ItemWithDepBHTIssue();
                depBHTIssue.setItemString(i.getItem().getName());
                depBHTIssue.setItemRate(i.getRetailsaleRate());
                depBHTIssue.setList(s1v3s);
                depBHTIssue.setItemTotal(total);
                totalBHTIssue += depBHTIssue.getItemTotal();
                itemWithDepBHTIssues.add(depBHTIssue);
            }
        }

    }

    public List<Department> fetchDepartments() {
        String sql;

        sql = " select d from Department d where d.retired=false ";

        return getDepartmentFacade().findBySQL(sql);
    }

    public List<ItemBatch> fetchPharmacyItemBatchs(Bill b) {
        String sql = "";
        Map m = new HashMap();

        sql = " select DISTINCT(bi.pharmaceuticalBillItem.itemBatch) "
                + " from BillItem bi where "
                + " bi.bill.retired=false "
                + " and type(bi.bill)=:dt "
                + " and bi.bill.createdAt between :fd and :td "
                + " and bi.bill.billType=:bt "
                + " order by bi.pharmaceuticalBillItem.itemBatch.item.name ";

        m.put("dt", b.getClass());
        m.put("bt", BillType.PharmacyBhtPre);
        m.put("fd", fromDate);
        m.put("td", toDate);

        return itemBatchFacade.findBySQL(sql, m, TemporalType.TIMESTAMP);
    }

    public List<Object[]> fetchPharmacyItemTotals(Department d, ItemBatch i, Bill b) {
        String sql = "";
        Map m = new HashMap();

        sql = " select SUM(bi.qty), "
                + " SUM(bi.netValue-bi.grossValue),"
                + " SUM(bi.netValue) "
                + " from BillItem bi where "
                + " bi.bill.retired=false "
                + " and type(bi.bill)=:dt "
                + " and bi.bill.createdAt between :fd and :td "
                + " and bi.bill.billType=:bt "
                + " and bi.item=:item "
                + " and bi.bill.department=:dep "
                + " and bi.netRate=:rate ";

        m.put("dt", b.getClass());
        m.put("dep", d);
        m.put("item", i.getItem());
        m.put("rate", i.getRetailsaleRate());
        m.put("bt", BillType.PharmacyBhtPre);
        m.put("fd", fromDate);
        m.put("td", toDate);

        return getBillItemFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);
    }

    public class ItemWithDepBHTIssue {

        String itemString;
        double itemRate;
        List<String1Value3> list;
        double itemTotal;

        public ItemWithDepBHTIssue() {
        }

        public String getItemString() {
            return itemString;
        }

        public void setItemString(String itemString) {
            this.itemString = itemString;
        }

        public List<String1Value3> getList() {
            return list;
        }

        public void setList(List<String1Value3> list) {
            this.list = list;
        }

        public double getItemRate() {
            return itemRate;
        }

        public void setItemRate(double itemRate) {
            this.itemRate = itemRate;
        }

        public double getItemTotal() {
            return itemTotal;
        }

        public void setItemTotal(double itemTotal) {
            this.itemTotal = itemTotal;
        }

    }

    public void createSaleReportByDateDetail() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getSaleValueByDepartmentByBill(nowDate, new BilledBill());
            double sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentByBill(nowDate, new CancelledBill());
            sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentByBill(nowDate, new RefundBill());
            sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calGrantNetTotalByDepartment(new BilledBill()));
        billedDetail.setDiscount(calGrantDiscountByDepartmentByBill(new BilledBill()));

        cancelledDetail.setNetTotal(calGrantNetTotalByDepartment(new CancelledBill()));
        cancelledDetail.setDiscount(calGrantDiscountByDepartmentByBill(new CancelledBill()));

        refundedDetail.setNetTotal(calGrantNetTotalByDepartment(new RefundBill()));
        refundedDetail.setDiscount(calGrantDiscountByDepartmentByBill(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();
        grantDiscount = calGrantDiscountByDepartmentByBill();

    }

    public void createSaleReportByDateDetailPaymentScheme() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new BilledBill());
            double sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new CancelledBill());
            sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new RefundBill());
            sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new BilledBill()));
        billedDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new BilledBill()));

        cancelledDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new CancelledBill()));
        cancelledDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new CancelledBill()));

        refundedDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new RefundBill()));
        refundedDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartmentPaymentScheme();
        grantDiscount = calGrantDiscountByDepartmentPaymentScheme();

    }

    List<PaymentSchemeSummery> paymentSchemeSummerys;
    double billTotal;
    double canTotal;
    double refTotal;

    @Inject
    PaymentSchemeController paymentSchemeController;

    public List<PaymentSchemeSummery> getPaymentSchemeSummerys() {
        return paymentSchemeSummerys;
    }

    public void setPaymentSchemeSummerys(List<PaymentSchemeSummery> paymentSchemeSummerys) {
        this.paymentSchemeSummerys = paymentSchemeSummerys;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public double getCanTotal() {
        return canTotal;
    }

    public void setCanTotal(double canTotal) {
        this.canTotal = canTotal;
    }

    public double getRefTotal() {
        return refTotal;
    }

    public void setRefTotal(double refTotal) {
        this.refTotal = refTotal;
    }

    public void createSalereportByDateSummeryPaymentscheam() {
        if (department == null) {
            UtilityController.addErrorMessage("Select Department");
            return;
        }
        paymentSchemeSummerys = new ArrayList<>();
        List<PaymentScheme> paymentSchemes = paymentSchemeController.getItems();

        billTotal = 0.0;
        canTotal = 0.0;
        refTotal = 0.0;

        for (PaymentScheme ps : paymentSchemes) {
            PaymentSchemeSummery pss = new PaymentSchemeSummery();

            pss.setPaymentScheme(ps.getName());
            pss.setBillTotal(getSaleValueByDepartmentPaymentSchemeP(new BilledBill(), ps));
            pss.setCancelBillTotal(getSaleValueByDepartmentPaymentSchemeP(new CancelledBill(), ps));
            pss.setRefundBillTotal(getSaleValueByDepartmentPaymentSchemeP(new RefundBill(), ps));

            billTotal += pss.getBillTotal();
            canTotal += pss.getCancelBillTotal();
            refTotal += pss.getRefundBillTotal();

            paymentSchemeSummerys.add(pss);
            System.out.println("Added - " + ps.getName());
        }

    }

    public class PaymentSchemeSummery {

        String paymentScheme;
        double billTotal;
        double cancelBillTotal;
        double refundBillTotal;

        public String getPaymentScheme() {
            return paymentScheme;
        }

        public void setPaymentScheme(String paymentScheme) {
            this.paymentScheme = paymentScheme;
        }

        public double getBillTotal() {
            return billTotal;
        }

        public void setBillTotal(double billTotal) {
            this.billTotal = billTotal;
        }

        public double getCancelBillTotal() {
            return cancelBillTotal;
        }

        public void setCancelBillTotal(double cancelBillTotal) {
            this.cancelBillTotal = cancelBillTotal;
        }

        public double getRefundBillTotal() {
            return refundBillTotal;
        }

        public void setRefundBillTotal(double refundBillTotal) {
            this.refundBillTotal = refundBillTotal;
        }

    }

    public void createIssueReportByDateDetail() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getIssueValueByDepartment(nowDate, new PreBill());
//            double sumDiscount = getDiscountValueByDepartment(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new PreBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getIssueValueByDepartment(nowDate, new CancelledBill());
//            sumDiscount = getDiscountValueByDepartment(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getIssueValueByDepartment(nowDate, new RefundBill());
//            sumDiscount = getDiscountValueByDepartment(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new PreBill()));
//        billedDetail.setDiscount(calGrantDiscountByDepartment(new BilledBill()));

        cancelledDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new CancelledBill()));
//        cancelledDetail.setDiscount(calGrantDiscountByDepartment(new CancelledBill()));

        refundedDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new RefundBill()));
//        refundedDetail.setDiscount(calGrantDiscountByDepartment(new RefundBill()));

        grantNetTotal = calIssueGrantNetTotalByDepartment();
//        grantDiscount = calGrantDiscountByDepartment();

    }

    public void createSalePaymentMethodDetailByBill() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumCash = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Cash, new BilledBill());
            double sumCredit = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Credit, new BilledBill());
            double sumCard = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Card, new BilledBill());
            double sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Cash, new CancelledBill());
            sumCredit = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Credit, new CancelledBill());
            sumCard = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Card, new CancelledBill());
            sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Cash, new RefundBill());
            sumCredit = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Credit, new RefundBill());
            sumCard = getSaleValueByDepartmentByBill(nowDate, PaymentMethod.Card, new RefundBill());
            sumDiscount = getDiscountValueByDepartmentByBill(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setDiscount(calGrantDiscountByDepartmentByBill(new BilledBill()));
        billedDetail.setCashTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Cash, new BilledBill()));
        billedDetail.setCreditTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Credit, new BilledBill()));
        billedDetail.setCardTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Card, new BilledBill()));

        cancelledDetail.setDiscount(calGrantDiscountByDepartmentByBill(new CancelledBill()));
        cancelledDetail.setCashTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Cash, new CancelledBill()));
        cancelledDetail.setCreditTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Credit, new CancelledBill()));
        cancelledDetail.setCardTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Card, new CancelledBill()));

        refundedDetail.setDiscount(calGrantDiscountByDepartmentByBill(new RefundBill()));
        refundedDetail.setCashTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Cash, new RefundBill()));
        refundedDetail.setCreditTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Credit, new RefundBill()));
        refundedDetail.setCardTotal(calGrantTotalByPaymentMethodByBill(PaymentMethod.Card, new RefundBill()));

        grantCardTotal = 0;
        grantCashTotal = 0;
        grantCreditTotal = 0;
        grantDiscount = 0;
        grantCardTotal = calGrantTotalByPaymentMethodByBill(PaymentMethod.Card);
        grantCashTotal = calGrantTotalByPaymentMethodByBill(PaymentMethod.Cash);
        grantCreditTotal = calGrantTotalByPaymentMethodByBill(PaymentMethod.Credit);
        grantDiscount = calGrantDiscountByDepartmentByBill();

    }

    public void createSalePaymentMethodDetailBillItems() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumCash = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Cash, new BilledBill());
            double sumCredit = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Credit, new BilledBill());
            double sumCard = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Card, new BilledBill());
            double sumDiscount = getDiscountValueByDepartmentByBillItem(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBillItems(getSaleBillItemByDepartment(nowDate, new BilledBill()));

            if (!newRow.getBillItems().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Cash, new CancelledBill());
            sumCredit = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Credit, new CancelledBill());
            sumCard = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Card, new CancelledBill());
            sumDiscount = getDiscountValueByDepartmentByBillItem(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBillItems(getSaleBillItemByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBillItems().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Cash, new RefundBill());
            sumCredit = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Credit, new RefundBill());
            sumCard = getSaleValueByDepartmentByBillItem(nowDate, PaymentMethod.Card, new RefundBill());
            sumDiscount = getDiscountValueByDepartmentByBillItem(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBillItems(getSaleBillItemByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBillItems().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setDiscount(calGrantDiscountByDepartmentByBillItem(new BilledBill()));
        billedDetail.setCashTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash, new BilledBill()));
        billedDetail.setCreditTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit, new BilledBill()));
        billedDetail.setCardTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card, new BilledBill()));

        cancelledDetail.setDiscount(calGrantDiscountByDepartmentByBillItem(new CancelledBill()));
        cancelledDetail.setCashTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash, new CancelledBill()));
        cancelledDetail.setCreditTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit, new CancelledBill()));
        cancelledDetail.setCardTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card, new CancelledBill()));

        refundedDetail.setDiscount(calGrantDiscountByDepartmentByBillItem(new RefundBill()));
        refundedDetail.setCashTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash, new RefundBill()));
        refundedDetail.setCreditTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit, new RefundBill()));
        refundedDetail.setCardTotal(calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card, new RefundBill()));

        grantCardTotal = 0;
        grantCashTotal = 0;
        grantCreditTotal = 0;
        grantDiscount = 0;
        grantCardTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Card);
        grantCashTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Cash);
        grantCreditTotal = calGrantTotalByPaymentMethodByBillItem(PaymentMethod.Credit);
        grantDiscount = calGrantDiscountByDepartmentByBillItem();

    }

    public void createItemListWithOutItemDistributer() {
        List<Amp> allAmps = getAllPharmacyItems();
        System.out.println("allAmps = " + allAmps.size());
        List<Amp> ampsWithDealor = getAllDealorItems();
        System.out.println("ampsWithOutDealor = " + ampsWithDealor.size());
        allAmps.removeAll(ampsWithDealor);
        System.out.println("After remove allAmps = " + allAmps.size());
        amps = new ArrayList<>();
        amps.addAll(allAmps);
        System.out.println("amps = " + amps.size());
    }

    public void createItemListOneItemHasGreterThanOneDistributor() {
        List<Object[]> objs = getAllDealorItemsWithCount();
        System.out.println("objs = " + objs);
        System.out.println("objs = " + objs.size());
        amps = new ArrayList<>();
        for (Object[] obj : objs) {
            System.out.println("obj = " + obj);
            if (obj != null) {
                Amp item = (Amp) obj[0];
                System.out.println("item = " + item.getName());
                long count = (long) obj[1];
                System.out.println("count = " + count);
                if (count > 1) {
                    System.out.println("****Add****");
                    amps.add(item);
                }
            }
        }
        System.out.println("items = " + amps.size());

    }

    public void createItemListOneItemHasGreterThanOneDistributorOther() {
        List<Amp> ampsWithDealor = getAllDealorItems();
        System.out.println("ampsWithDealor = " + ampsWithDealor.size());

        items = new ArrayList<>();
        for (Item i : ampsWithDealor) {
            System.err.println("in");
            System.out.println("item = " + i.getName());
            List<Amp> allAmps = getAmpItems(i);
            System.out.println("amps = " + allAmps.size());
            int count = 0;
            if (allAmps != null) {
                count = allAmps.size();
            }
            System.out.println("count = " + count);
            if (count > 1) {
                System.out.println("****Add****");
                items.add(i);
            }
            System.err.println("out");
        }
        System.out.println("items = " + items.size());

    }

    public List<Amp> getAllPharmacyItems() {
        String sql;
        Map m = new HashMap();
        m.put("dep", DepartmentType.Store);
        m.put("dep2", DepartmentType.Inventry);
        sql = "select c from Amp c "
                + " where c.retired=false "
                + " and (c.departmentType is null "
                + " or c.departmentType!=:dep "
                + " or c.departmentType!=:dep2 )"
                + " order by c.name ";

        return ampFacade.findBySQL(sql, m);
    }

    public List<Amp> getAllDealorItems() {
        String sql;

        sql = "SELECT distinct(i.item) FROM ItemsDistributors i "
                + " where i.retired=false "
                + " and i.item.retired=false "
                + " order by i.item.name ";

        return ampFacade.findBySQL(sql);
    }

    public List<Object[]> getAllDealorItemsWithCount() {
        String sql;

        sql = "SELECT distinct(i.item),count(i.item) FROM ItemsDistributors i "
                + " where i.retired=false "
                + " and i.item.retired=false "
                + " order by i.item.name ";

        return ampFacade.findAggregates(sql);
    }

    public List<Amp> getAmpItems(Item a) {
        String sql;
        Map m = new HashMap();

        sql = "SELECT i.item FROM ItemsDistributors i "
                + " where i.retired=false "
                + " and i.item.retired=false "
                + " and i.item=:a ";

        m.put("a", a);
        return ampFacade.findBySQL(sql, m);
    }

    /**
     * Creates a new instance of PharmacySaleReport
     */
    public PharmacySaleReport() {
    }

    public Date getFromDate() {
        if (fromDate == null) {

            fromDate = com.divudi.java.CommonFunctions.getStartOfMonth(new Date());
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = com.divudi.java.CommonFunctions.getEndOfMonth(new Date());
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CommonFunctions getCommonFunctions() {
        return commonFunctions;
    }

    public void setCommonFunctions(CommonFunctions commonFunctions) {
        this.commonFunctions = commonFunctions;
    }

    public BillItemFacade getBillItemFacade() {
        return billItemFacade;
    }

    public void setBillItemFacade(BillItemFacade billItemFacade) {
        this.billItemFacade = billItemFacade;
    }

    public double getGrantNetTotal() {
        return grantNetTotal;
    }

    public void setGrantNetTotal(double grantNetTotal) {
        this.grantNetTotal = grantNetTotal;
    }

    public BillFacade getBillFacade() {
        return billFacade;
    }

    public void setBillFacade(BillFacade billFacade) {
        this.billFacade = billFacade;
    }

    public double getGrantDiscount() {
        return grantDiscount;
    }

    public void setGrantDiscount(double grantDiscount) {
        this.grantDiscount = grantDiscount;
    }

    public PharmacySummery getBilledSummery() {
        return billedSummery;
    }

    public void setBilledSummery(PharmacySummery billedSummery) {
        this.billedSummery = billedSummery;
    }

    public PharmacyPaymetMethodSummery getBilledPaymentSummery() {
        return billedPaymentSummery;
    }

    public void setBilledPaymentSummery(PharmacyPaymetMethodSummery billedPaymentSummery) {
        this.billedPaymentSummery = billedPaymentSummery;
    }

    public double getGrantCashTotal() {
        return grantCashTotal;
    }

    public void setGrantCashTotal(double grantCashTotal) {
        this.grantCashTotal = grantCashTotal;
    }

    public double getGrantCreditTotal() {
        return grantCreditTotal;
    }

    public void setGrantCreditTotal(double grantCreditTotal) {
        this.grantCreditTotal = grantCreditTotal;
    }

    public double getGrantCardTotal() {
        return grantCardTotal;
    }

    public void setGrantCardTotal(double grantCardTotal) {
        this.grantCardTotal = grantCardTotal;
    }

    public PharmacyDetail getBilledDetail() {
        return billedDetail;
    }

    public void setBilledDetail(PharmacyDetail billedDetail) {
        this.billedDetail = billedDetail;
    }

    public PharmacyDetail getCancelledDetail() {
        return cancelledDetail;
    }

    public void setCancelledDetail(PharmacyDetail cancelledDetail) {
        this.cancelledDetail = cancelledDetail;
    }

    public PharmacyDetail getRefundedDetail() {
        return refundedDetail;
    }

    public void setRefundedDetail(PharmacyDetail refundedDetail) {
        this.refundedDetail = refundedDetail;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public double getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(double grantTotal) {
        this.grantTotal = grantTotal;
    }

    public double getGrantProfessional() {
        return grantProfessional;
    }

    public void setGrantProfessional(double grantProfessional) {
        this.grantProfessional = grantProfessional;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public PaymentScheme getPaymentScheme() {
        return paymentScheme;
    }

    public void setPaymentScheme(PaymentScheme paymentScheme) {
        this.paymentScheme = paymentScheme;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<String1Value6> getSaleValuesCash() {
        return saleValuesCash;
    }

    public void setSaleValuesCash(List<String1Value6> saleValuesCash) {
        this.saleValuesCash = saleValuesCash;
    }

    public List<String1Value6> getSaleValuesCheque() {
        return saleValuesCheque;
    }

    public void setSaleValuesCheque(List<String1Value6> saleValuesCheque) {
        this.saleValuesCheque = saleValuesCheque;
    }

    public DepartmentFacade getDepartmentFacade() {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    public List<String1Value6> getSaleValuesSlip() {
        return saleValuesSlip;
    }

    public void setSaleValuesSlip(List<String1Value6> saleValuesSlip) {
        this.saleValuesSlip = saleValuesSlip;
    }

    public List<String1Value6> getSaleValuesCard() {
        return saleValuesCard;
    }

    public void setSaleValuesCard(List<String1Value6> saleValuesCard) {
        this.saleValuesCard = saleValuesCard;
    }

    public List<String1Value6> getSaleValuesCredit() {
        return saleValuesCredit;
    }

    public void setSaleValuesCredit(List<String1Value6> saleValuesCredit) {
        this.saleValuesCredit = saleValuesCredit;
    }

    public List<String1Value6> getBhtIssues() {
        return bhtIssues;
    }

    public void setBhtIssues(List<String1Value6> bhtIssues) {
        this.bhtIssues = bhtIssues;
    }

    public List<String1Value6> getUnitIssues() {
        return unitIssues;
    }

    public void setUnitIssues(List<String1Value6> unitIssues) {
        this.unitIssues = unitIssues;
    }

    public List<String1Value6> getWholeSaleValuesCash() {
        return wholeSaleValuesCash;
    }

    public void setWholeSaleValuesCash(List<String1Value6> wholeSaleValuesCash) {
        this.wholeSaleValuesCash = wholeSaleValuesCash;
    }

    public List<String1Value6> getWholeSaleValuesCheque() {
        return wholeSaleValuesCheque;
    }

    public void setWholeSaleValuesCheque(List<String1Value6> wholeSaleValuesCheque) {
        this.wholeSaleValuesCheque = wholeSaleValuesCheque;
    }

    public List<String1Value6> getWholeSaleValuesSlip() {
        return wholeSaleValuesSlip;
    }

    public void setWholeSaleValuesSlip(List<String1Value6> wholeSaleValuesSlip) {
        this.wholeSaleValuesSlip = wholeSaleValuesSlip;
    }

    public List<String1Value6> getWholeSaleValuesCard() {
        return wholeSaleValuesCard;
    }

    public void setWholeSaleValuesCard(List<String1Value6> wholeSaleValuesCard) {
        this.wholeSaleValuesCard = wholeSaleValuesCard;
    }

    public List<String1Value6> getWholeSaleValuesCredit() {
        return wholeSaleValuesCredit;
    }

    public void setWholeSaleValuesCredit(List<String1Value6> wholeSaleValuesCredit) {
        this.wholeSaleValuesCredit = wholeSaleValuesCredit;
    }
    
    

    public double getTotalPSCashBV() {
        return totalPSCashBV;
    }

    public void setTotalPSCashBV(double totalPSCashBV) {
        this.totalPSCashBV = totalPSCashBV;
    }

    public double getTotalPSCashRV() {
        return totalPSCashRV;
    }

    public void setTotalPSCashRV(double totalPSCashRV) {
        this.totalPSCashRV = totalPSCashRV;
    }

    public double getTotalPSCashNV() {
        return totalPSCashNV;
    }

    public void setTotalPSCashNV(double totalPSCashNV) {
        this.totalPSCashNV = totalPSCashNV;
    }

    public double getTotalPSCashBC() {
        return totalPSCashBC;
    }

    public void setTotalPSCashBC(double totalPSCashBC) {
        this.totalPSCashBC = totalPSCashBC;
    }

    public double getTotalPSCashRC() {
        return totalPSCashRC;
    }

    public void setTotalPSCashRC(double totalPSCashRC) {
        this.totalPSCashRC = totalPSCashRC;
    }

    public double getTotalPSCashNC() {
        return totalPSCashNC;
    }

    public void setTotalPSCashNC(double totalPSCashNC) {
        this.totalPSCashNC = totalPSCashNC;
    }

    public double getTotalPSCreditBV() {
        return totalPSCreditBV;
    }

    public void setTotalPSCreditBV(double totalPSCreditBV) {
        this.totalPSCreditBV = totalPSCreditBV;
    }

    public double getTotalPSCreditRV() {
        return totalPSCreditRV;
    }

    public void setTotalPSCreditRV(double totalPSCreditRV) {
        this.totalPSCreditRV = totalPSCreditRV;
    }

    public double getTotalPSCreditNV() {
        return totalPSCreditNV;
    }

    public void setTotalPSCreditNV(double totalPSCreditNV) {
        this.totalPSCreditNV = totalPSCreditNV;
    }

    public double getTotalPSCreditBC() {
        return totalPSCreditBC;
    }

    public void setTotalPSCreditBC(double totalPSCreditBC) {
        this.totalPSCreditBC = totalPSCreditBC;
    }

    public double getTotalPSCreditRC() {
        return totalPSCreditRC;
    }

    public void setTotalPSCreditRC(double totalPSCreditRC) {
        this.totalPSCreditRC = totalPSCreditRC;
    }

    public double getTotalPSCreditNC() {
        return totalPSCreditNC;
    }

    public void setTotalPSCreditNC(double totalPSCreditNC) {
        this.totalPSCreditNC = totalPSCreditNC;
    }

    public double getTotalPSCardBV() {
        return totalPSCardBV;
    }

    public void setTotalPSCardBV(double totalPSCardBV) {
        this.totalPSCardBV = totalPSCardBV;
    }

    public double getTotalPSCardRV() {
        return totalPSCardRV;
    }

    public void setTotalPSCardRV(double totalPSCardRV) {
        this.totalPSCardRV = totalPSCardRV;
    }

    public double getTotalPSCardNV() {
        return totalPSCardNV;
    }

    public void setTotalPSCardNV(double totalPSCardNV) {
        this.totalPSCardNV = totalPSCardNV;
    }

    public double getTotalPSCardBC() {
        return totalPSCardBC;
    }

    public void setTotalPSCardBC(double totalPSCardBC) {
        this.totalPSCardBC = totalPSCardBC;
    }

    public double getTotalPSCardRC() {
        return totalPSCardRC;
    }

    public void setTotalPSCardRC(double totalPSCardRC) {
        this.totalPSCardRC = totalPSCardRC;
    }

    public double getTotalPSCardNC() {
        return totalPSCardNC;
    }

    public void setTotalPSCardNC(double totalPSCardNC) {
        this.totalPSCardNC = totalPSCardNC;
    }

    public double getTotalPSSlipBV() {
        return totalPSSlipBV;
    }

    public void setTotalPSSlipBV(double totalPSSlipBV) {
        this.totalPSSlipBV = totalPSSlipBV;
    }

    public double getTotalPSSlipRV() {
        return totalPSSlipRV;
    }

    public void setTotalPSSlipRV(double totalPSSlipRV) {
        this.totalPSSlipRV = totalPSSlipRV;
    }

    public double getTotalPSSlipNV() {
        return totalPSSlipNV;
    }

    public void setTotalPSSlipNV(double totalPSSlipNV) {
        this.totalPSSlipNV = totalPSSlipNV;
    }

    public double getTotalPSSlipBC() {
        return totalPSSlipBC;
    }

    public void setTotalPSSlipBC(double totalPSSlipBC) {
        this.totalPSSlipBC = totalPSSlipBC;
    }

    public double getTotalPSSlipRC() {
        return totalPSSlipRC;
    }

    public void setTotalPSSlipRC(double totalPSSlipRC) {
        this.totalPSSlipRC = totalPSSlipRC;
    }

    public double getTotalPSSlipNC() {
        return totalPSSlipNC;
    }

    public void setTotalPSSlipNC(double totalPSSlipNC) {
        this.totalPSSlipNC = totalPSSlipNC;
    }

    public double getTotalPSChequeBV() {
        return totalPSChequeBV;
    }

    public void setTotalPSChequeBV(double totalPSChequeBV) {
        this.totalPSChequeBV = totalPSChequeBV;
    }

    public double getTotalPSChequeRV() {
        return totalPSChequeRV;
    }

    public void setTotalPSChequeRV(double totalPSChequeRV) {
        this.totalPSChequeRV = totalPSChequeRV;
    }

    public double getTotalPSChequeNV() {
        return totalPSChequeNV;
    }

    public void setTotalPSChequeNV(double totalPSChequeNV) {
        this.totalPSChequeNV = totalPSChequeNV;
    }

    public double getTotalPSChequeBC() {
        return totalPSChequeBC;
    }

    public void setTotalPSChequeBC(double totalPSChequeBC) {
        this.totalPSChequeBC = totalPSChequeBC;
    }

    public double getTotalPSChequeRC() {
        return totalPSChequeRC;
    }

    public void setTotalPSChequeRC(double totalPSChequeRC) {
        this.totalPSChequeRC = totalPSChequeRC;
    }

    public double getTotalPSChequeNC() {
        return totalPSChequeNC;
    }

    public void setTotalPSChequeNC(double totalPSChequeNC) {
        this.totalPSChequeNC = totalPSChequeNC;
    }

    public double getTotalPWSCashBV() {
        return totalPWSCashBV;
    }

    public void setTotalPWSCashBV(double totalPWSCashBV) {
        this.totalPWSCashBV = totalPWSCashBV;
    }

    public double getTotalPWSCashCV() {
        return totalPWSCashCV;
    }

    public void setTotalPWSCashCV(double totalPWSCashCV) {
        this.totalPWSCashCV = totalPWSCashCV;
    }

    public double getTotalPWSCashRV() {
        return totalPWSCashRV;
    }

    public void setTotalPWSCashRV(double totalPWSCashRV) {
        this.totalPWSCashRV = totalPWSCashRV;
    }

    public double getTotalPWSCashNV() {
        return totalPWSCashNV;
    }

    public void setTotalPWSCashNV(double totalPWSCashNV) {
        this.totalPWSCashNV = totalPWSCashNV;
    }

    public double getTotalPWSCashBC() {
        return totalPWSCashBC;
    }

    public void setTotalPWSCashBC(double totalPWSCashBC) {
        this.totalPWSCashBC = totalPWSCashBC;
    }

    public double getTotalPWSCashRC() {
        return totalPWSCashRC;
    }

    public void setTotalPWSCashRC(double totalPWSCashRC) {
        this.totalPWSCashRC = totalPWSCashRC;
    }

    public double getTotalPWSCashNC() {
        return totalPWSCashNC;
    }

    public void setTotalPWSCashNC(double totalPWSCashNC) {
        this.totalPWSCashNC = totalPWSCashNC;
    }

    public double getTotalPWSCreditBV() {
        return totalPWSCreditBV;
    }

    public void setTotalPWSCreditBV(double totalPWSCreditBV) {
        this.totalPWSCreditBV = totalPWSCreditBV;
    }

    public double getTotalPWSCreditCV() {
        return totalPWSCreditCV;
    }

    public void setTotalPWSCreditCV(double totalPWSCreditCV) {
        this.totalPWSCreditCV = totalPWSCreditCV;
    }

    public double getTotalPWSCreditRV() {
        return totalPWSCreditRV;
    }

    public void setTotalPWSCreditRV(double totalPWSCreditRV) {
        this.totalPWSCreditRV = totalPWSCreditRV;
    }

    public double getTotalPWSCreditNV() {
        return totalPWSCreditNV;
    }

    public void setTotalPWSCreditNV(double totalPWSCreditNV) {
        this.totalPWSCreditNV = totalPWSCreditNV;
    }

    public double getTotalPWSCreditBC() {
        return totalPWSCreditBC;
    }

    public void setTotalPWSCreditBC(double totalPWSCreditBC) {
        this.totalPWSCreditBC = totalPWSCreditBC;
    }

    public double getTotalPWSCreditRC() {
        return totalPWSCreditRC;
    }

    public void setTotalPWSCreditRC(double totalPWSCreditRC) {
        this.totalPWSCreditRC = totalPWSCreditRC;
    }

    public double getTotalPWSCreditNC() {
        return totalPWSCreditNC;
    }

    public void setTotalPWSCreditNC(double totalPWSCreditNC) {
        this.totalPWSCreditNC = totalPWSCreditNC;
    }

    public double getTotalPWSCardBV() {
        return totalPWSCardBV;
    }

    public void setTotalPWSCardBV(double totalPWSCardBV) {
        this.totalPWSCardBV = totalPWSCardBV;
    }

    public double getTotalPWSCardCV() {
        return totalPWSCardCV;
    }

    public void setTotalPWSCardCV(double totalPWSCardCV) {
        this.totalPWSCardCV = totalPWSCardCV;
    }

    public double getTotalPWSCardRV() {
        return totalPWSCardRV;
    }

    public void setTotalPWSCardRV(double totalPWSCardRV) {
        this.totalPWSCardRV = totalPWSCardRV;
    }

    public double getTotalPWSCardNV() {
        return totalPWSCardNV;
    }

    public void setTotalPWSCardNV(double totalPWSCardNV) {
        this.totalPWSCardNV = totalPWSCardNV;
    }

    public double getTotalPWSCardBC() {
        return totalPWSCardBC;
    }

    public void setTotalPWSCardBC(double totalPWSCardBC) {
        this.totalPWSCardBC = totalPWSCardBC;
    }

    public double getTotalPWSCardRC() {
        return totalPWSCardRC;
    }

    public void setTotalPWSCardRC(double totalPWSCardRC) {
        this.totalPWSCardRC = totalPWSCardRC;
    }

    public double getTotalPWSCardNC() {
        return totalPWSCardNC;
    }

    public void setTotalPWSCardNC(double totalPWSCardNC) {
        this.totalPWSCardNC = totalPWSCardNC;
    }

    public double getTotalPWSSlipBV() {
        return totalPWSSlipBV;
    }

    public void setTotalPWSSlipBV(double totalPWSSlipBV) {
        this.totalPWSSlipBV = totalPWSSlipBV;
    }

    public double getTotalPWSSlipCV() {
        return totalPWSSlipCV;
    }

    public void setTotalPWSSlipCV(double totalPWSSlipCV) {
        this.totalPWSSlipCV = totalPWSSlipCV;
    }

    public double getTotalPWSSlipRV() {
        return totalPWSSlipRV;
    }

    public void setTotalPWSSlipRV(double totalPWSSlipRV) {
        this.totalPWSSlipRV = totalPWSSlipRV;
    }

    public double getTotalPWSSlipNV() {
        return totalPWSSlipNV;
    }

    public void setTotalPWSSlipNV(double totalPWSSlipNV) {
        this.totalPWSSlipNV = totalPWSSlipNV;
    }

    public double getTotalPWSSlipBC() {
        return totalPWSSlipBC;
    }

    public void setTotalPWSSlipBC(double totalPWSSlipBC) {
        this.totalPWSSlipBC = totalPWSSlipBC;
    }

    public double getTotalPWSSlipRC() {
        return totalPWSSlipRC;
    }

    public void setTotalPWSSlipRC(double totalPWSSlipRC) {
        this.totalPWSSlipRC = totalPWSSlipRC;
    }

    public double getTotalPWSSlipNC() {
        return totalPWSSlipNC;
    }

    public void setTotalPWSSlipNC(double totalPWSSlipNC) {
        this.totalPWSSlipNC = totalPWSSlipNC;
    }

    public double getTotalPWSChequeBV() {
        return totalPWSChequeBV;
    }

    public void setTotalPWSChequeBV(double totalPWSChequeBV) {
        this.totalPWSChequeBV = totalPWSChequeBV;
    }

    public double getTotalPWSChequeCV() {
        return totalPWSChequeCV;
    }

    public void setTotalPWSChequeCV(double totalPWSChequeCV) {
        this.totalPWSChequeCV = totalPWSChequeCV;
    }

    public double getTotalPWSChequeRV() {
        return totalPWSChequeRV;
    }

    public void setTotalPWSChequeRV(double totalPWSChequeRV) {
        this.totalPWSChequeRV = totalPWSChequeRV;
    }

    public double getTotalPWSChequeNV() {
        return totalPWSChequeNV;
    }

    public void setTotalPWSChequeNV(double totalPWSChequeNV) {
        this.totalPWSChequeNV = totalPWSChequeNV;
    }

    public double getTotalPWSChequeBC() {
        return totalPWSChequeBC;
    }

    public void setTotalPWSChequeBC(double totalPWSChequeBC) {
        this.totalPWSChequeBC = totalPWSChequeBC;
    }

    public double getTotalPWSChequeRC() {
        return totalPWSChequeRC;
    }

    public void setTotalPWSChequeRC(double totalPWSChequeRC) {
        this.totalPWSChequeRC = totalPWSChequeRC;
    }

    public double getTotalPWSChequeNC() {
        return totalPWSChequeNC;
    }

    public void setTotalPWSChequeNC(double totalPWSChequeNC) {
        this.totalPWSChequeNC = totalPWSChequeNC;
    }

    public double getTotalBHTIssueBV() {
        return totalBHTIssueBV;
    }

    public void setTotalBHTIssueBV(double totalBHTIssueBV) {
        this.totalBHTIssueBV = totalBHTIssueBV;
    }

    public double getTotalBHTIssueRV() {
        return totalBHTIssueRV;
    }

    public void setTotalBHTIssueRV(double totalBHTIssueRV) {
        this.totalBHTIssueRV = totalBHTIssueRV;
    }

    public double getTotalBHTIssueNV() {
        return totalBHTIssueNV;
    }

    public void setTotalBHTIssueNV(double totalBHTIssueNV) {
        this.totalBHTIssueNV = totalBHTIssueNV;
    }

    public double getTotalBHTIssueBC() {
        return totalBHTIssueBC;
    }

    public void setTotalBHTIssueBC(double totalBHTIssueBC) {
        this.totalBHTIssueBC = totalBHTIssueBC;
    }

    public double getTotalBHTIssueRC() {
        return totalBHTIssueRC;
    }

    public void setTotalBHTIssueRC(double totalBHTIssueRC) {
        this.totalBHTIssueRC = totalBHTIssueRC;
    }

    public double getTotalBHTIssueNC() {
        return totalBHTIssueNC;
    }

    public void setTotalBHTIssueNC(double totalBHTIssueNC) {
        this.totalBHTIssueNC = totalBHTIssueNC;
    }

    public double getTotalUnitIssueBV() {
        return totalUnitIssueBV;
    }

    public void setTotalUnitIssueBV(double totalUnitIssueBV) {
        this.totalUnitIssueBV = totalUnitIssueBV;
    }

    public double getTotalUnitIssueRV() {
        return totalUnitIssueRV;
    }

    public void setTotalUnitIssueRV(double totalUnitIssueRV) {
        this.totalUnitIssueRV = totalUnitIssueRV;
    }

    public double getTotalUnitIssueNV() {
        return totalUnitIssueNV;
    }

    public void setTotalUnitIssueNV(double totalUnitIssueNV) {
        this.totalUnitIssueNV = totalUnitIssueNV;
    }

    public double getTotalUnitIssueBC() {
        return totalUnitIssueBC;
    }

    public void setTotalUnitIssueBC(double totalUnitIssueBC) {
        this.totalUnitIssueBC = totalUnitIssueBC;
    }

    public double getTotalUnitIssueRC() {
        return totalUnitIssueRC;
    }

    public void setTotalUnitIssueRC(double totalUnitIssueRC) {
        this.totalUnitIssueRC = totalUnitIssueRC;
    }

    public double getTotalUnitIssueNC() {
        return totalUnitIssueNC;
    }

    public void setTotalUnitIssueNC(double totalUnitIssueNC) {
        this.totalUnitIssueNC = totalUnitIssueNC;
    }

    public double getTotalOpdSale() {
        return totalOpdSale;
    }

    public void setTotalOpdSale(double totalOpdSale) {
        this.totalOpdSale = totalOpdSale;
    }

    public double getTotalInwardIssue() {
        return totalInwardIssue;
    }

    public void setTotalInwardIssue(double totalInwardIssue) {
        this.totalInwardIssue = totalInwardIssue;
    }

    public double getTotalDepartmentIssue() {
        return totalDepartmentIssue;
    }

    public void setTotalDepartmentIssue(double totalDepartmentIssue) {
        this.totalDepartmentIssue = totalDepartmentIssue;
    }

    public double getTotalTatalValue() {
        return totalTatalValue;
    }

    public void setTotalTatalValue(double totalTatalValue) {
        this.totalTatalValue = totalTatalValue;
    }

    public double getTotalPurchaseValue() {
        return totalPurchaseValue;
    }

    public void setTotalPurchaseValue(double totalPurchaseValue) {
        this.totalPurchaseValue = totalPurchaseValue;
    }

    public double getTotalMargineValue() {
        return totalMargineValue;
    }

    public void setTotalMargineValue(double totalMargineValue) {
        this.totalMargineValue = totalMargineValue;
    }

    public double getTotalPSCashCV() {
        return totalPSCashCV;
    }

    public void setTotalPSCashCV(double totalPSCashCV) {
        this.totalPSCashCV = totalPSCashCV;
    }

    public double getTotalPSCreditCV() {
        return totalPSCreditCV;
    }

    public void setTotalPSCreditCV(double totalPSCreditCV) {
        this.totalPSCreditCV = totalPSCreditCV;
    }

    public double getTotalPSCardCV() {
        return totalPSCardCV;
    }

    public void setTotalPSCardCV(double totalPSCardCV) {
        this.totalPSCardCV = totalPSCardCV;
    }

    public double getTotalPSSlipCV() {
        return totalPSSlipCV;
    }

    public void setTotalPSSlipCV(double totalPSSlipCV) {
        this.totalPSSlipCV = totalPSSlipCV;
    }

    public double getTotalPSChequeCV() {
        return totalPSChequeCV;
    }

    public void setTotalPSChequeCV(double totalPSChequeCV) {
        this.totalPSChequeCV = totalPSChequeCV;
    }

    public double getTotalBHTIssueCV() {
        return totalBHTIssueCV;
    }

    public void setTotalBHTIssueCV(double totalBHTIssueCV) {
        this.totalBHTIssueCV = totalBHTIssueCV;
    }

    public double getTotalUnitIssueCV() {
        return totalUnitIssueCV;
    }

    public void setTotalUnitIssueCV(double totalUnitIssueCV) {
        this.totalUnitIssueCV = totalUnitIssueCV;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public SearchKeyword getSearchKeyword() {
        if (searchKeyword == null) {
            searchKeyword = new SearchKeyword();
        }
        return searchKeyword;
    }

    public void setSearchKeyword(SearchKeyword searchKeyword) {
        this.searchKeyword = searchKeyword;

    }

    public List<Bill> getLabBhtIssueBilledBills() {
        return labBhtIssueBilledBills;
    }

    public void setLabBhtIssueBilledBills(List<Bill> labBhtIssueBilledBills) {
        this.labBhtIssueBilledBills = labBhtIssueBilledBills;
    }

    public double getLabBhtIssueBilledBillTotals() {
        return labBhtIssueBilledBillTotals;
    }

    public void setLabBhtIssueBilledBillTotals(double labBhtIssueBilledBillTotals) {
        this.labBhtIssueBilledBillTotals = labBhtIssueBilledBillTotals;
    }

    public class CategoryMovementReportRow {

        Item item;
        ItemBatch itemBatch;
        Stock stock;
        double opdSale;
        double opdSaleQty;
        double inwardIssue;
        double inwardIssueQty;
        double departmentIssue;
        double departmentIssueQty;
        double total;
        double purchaseValue;
        double marginValue;
        double transfer;
        double transferIn;
        double transferInQty;
        double transferOut;
        double transferOutQty;

        public Stock getStock() {
            return stock;
        }

        public void setStock(Stock stock) {
            this.stock = stock;
        }

        public ItemBatch getItemBatch() {
            return itemBatch;
        }

        public void setItemBatch(ItemBatch itemBatch) {
            this.itemBatch = itemBatch;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public double getOpdSale() {
            return opdSale;
        }

        public void setOpdSale(double opdSale) {
            this.opdSale = opdSale;
        }

        public double getInwardIssue() {
            return inwardIssue;
        }

        public void setInwardIssue(double inwardIssue) {
            this.inwardIssue = inwardIssue;
        }

        public double getDepartmentIssue() {
            return departmentIssue;
        }

        public void setDepartmentIssue(double departmentIssue) {
            this.departmentIssue = departmentIssue;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getPurchaseValue() {
            return purchaseValue;
        }

        public void setPurchaseValue(double purchaseValue) {
            this.purchaseValue = purchaseValue;
        }

        public double getMarginValue() {
            return marginValue;
        }

        public void setMarginValue(double marginValue) {
            this.marginValue = marginValue;
        }

        public double getTransfer() {
            return transfer;
        }

        public void setTransfer(double transfer) {
            this.transfer = transfer;
        }

        public double getTransferIn() {
            return transferIn;
        }

        public void setTransferIn(double transferIn) {
            this.transferIn = transferIn;
        }

        public double getTransferOut() {
            return transferOut;
        }

        public void setTransferOut(double transferOut) {
            this.transferOut = transferOut;
        }

        public double getOpdSaleQty() {
            return opdSaleQty;
        }

        public void setOpdSaleQty(double opdSaleQty) {
            this.opdSaleQty = opdSaleQty;
        }

        public double getInwardIssueQty() {
            return inwardIssueQty;
        }

        public void setInwardIssueQty(double inwardIssueQty) {
            this.inwardIssueQty = inwardIssueQty;
        }

        public double getDepartmentIssueQty() {
            return departmentIssueQty;
        }

        public void setDepartmentIssueQty(double departmentIssueQty) {
            this.departmentIssueQty = departmentIssueQty;
        }

        public double getTransferInQty() {
            return transferInQty;
        }

        public void setTransferInQty(double transferInQty) {
            this.transferInQty = transferInQty;
        }

        public double getTransferOutQty() {
            return transferOutQty;
        }

        public void setTransferOutQty(double transferOutQty) {
            this.transferOutQty = transferOutQty;
        }

    }

    public Department getDepartmentMoving() {
        return departmentMoving;
    }

    public void setDepartmentMoving(Department departmentMoving) {
        this.departmentMoving = departmentMoving;
    }

    public List<Amp> getAmps() {
        if (amps == null) {
            amps = new ArrayList<>();
        }
        return amps;
    }

    public void setAmps(List<Amp> amps) {
        this.amps = amps;
    }

    public List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
