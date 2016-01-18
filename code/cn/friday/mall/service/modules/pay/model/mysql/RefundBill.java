package cn.friday.mall.service.modules.pay.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

/**
 *
 * @author super_auto_code
 *
 */
@Entity
@Table(name = "mall_refund_bill")
public class RefundBill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;      

    // 订单id
    @Column(name = "order_id")
    private long orderId;      

    // 退款单号
    @Column(name = "out_refund_no")
    private String outRefundNo;      

    // 微信退款流水号
    @Column(name = "refund_id")
    private String refundId;      

    // 退款金额
    @Column(name = "refund_fee")
    private int refundFee;      

    // 操作人id
    @Column(name = "student_id")
    private long studentId;      

    // 退款状态:0失败，1成功，2正在进行中
    @Column(name = "status")
    private int status;      

    // 添加时间
    @Column(name = "add_time")
    private Date addTime;      


     public void setId(long id){  
	      this.id = id;  
     }
     
     public long getId(){  
	      return this.id;  
     } 

     public void setOrderId(long orderId){  
	      this.orderId = orderId;  
     }
     
     public long getOrderId(){  
	      return this.orderId;  
     } 

     public void setOutRefundNo(String outRefundNo){  
	      this.outRefundNo = outRefundNo;  
     }
     
     public String getOutRefundNo(){  
	      return this.outRefundNo;  
     } 

     public void setRefundId(String refundId){  
	      this.refundId = refundId;  
     }
     
     public String getRefundId(){  
	      return this.refundId;  
     } 

     public void setRefundFee(int refundFee){  
	      this.refundFee = refundFee;  
     }
     
     public int getRefundFee(){  
	      return this.refundFee;  
     } 

     public void setStudentId(long studentId){  
	      this.studentId = studentId;  
     }
     
     public long getStudentId(){  
	      return this.studentId;  
     } 

     public void setStatus(int status){  
	      this.status = status;  
     }
     
     public int getStatus(){  
	      return this.status;  
     } 

     public void setAddTime(Date addTime){  
	      this.addTime = addTime;  
     }
     
     public Date getAddTime(){  
	      return this.addTime;  
     } 
	
}
