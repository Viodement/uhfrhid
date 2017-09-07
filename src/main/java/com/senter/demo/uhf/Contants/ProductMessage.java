package com.senter.demo.uhf.Contants;

/**
 * Created by Lenovo on 2017/6/15.
 */
public class ProductMessage {
    private String productName;
    private String productStatus;
    private String productMore;

    public ProductMessage(String productName,String productStatus,String productMore){
        super();
        this.productName = productName;
        this.productStatus = productStatus;
        this.productMore = productMore;
    }

    public String getProductMore() {
        return productMore;
    }

    public void setProductMore(String productMore) {
        this.productMore = productMore;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
