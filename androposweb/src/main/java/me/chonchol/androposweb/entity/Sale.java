package me.chonchol.androposweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@SqlResultSetMapping(name = "SaleReportResult",
        classes = {
                @ConstructorResult(
                        targetClass = me.chonchol.androposweb.entity.report.SaleReport.class,
                        columns = {
                                @ColumnResult(name = "date"),
                                @ColumnResult(name = "customerName"),
                                @ColumnResult(name = "phoneNo"),
                                @ColumnResult(name = "productName"),
                                @ColumnResult(name = "quantity"),
                                @ColumnResult(name = "totalAmount"),
                        })
        }
)
@Table(name = "SALE")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer saleId;

    @OneToOne
    @JoinColumn(name = "quotation_id")
    private Quotation quotation;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
}
