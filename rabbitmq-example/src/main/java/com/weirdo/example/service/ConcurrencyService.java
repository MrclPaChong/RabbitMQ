package com.weirdo.example.service;


import com.weirdo.example.entity.Product;
import com.weirdo.example.entity.ProductRobbingRecord;
import com.weirdo.example.mapper.ProductMapper;
import com.weirdo.example.mapper.ProductRobbingRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/8/25.
 */
@Service
public class ConcurrencyService {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyService.class);

    private static final String ProductNo="product_10010";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRobbingRecordMapper productRobbingRecordMapper;

    /**
     * 处理抢单
     * @param mobile
     */
    public void manageRobbing(String mobile){
        /*try {
            Product product=productMapper.selectByProductNo(ProductNo);
            if (product!=null && product.getTotal()>0){
                log.info("当前手机号：{} 恭喜您抢到单了!",mobile);
                productMapper.updateTotal(product.getProduct_no());
            }else{
                log.error("当前手机号：{} 抢不到单!",mobile);

            }
        }catch (Exception e){
            log.error("处理抢单发生异常：mobile={} ",mobile);
        }*/ //--v1.0


        //+v2.0
        try {
            //根据商品编号查询出商品信息
            Product product=productMapper.selectByProductNo(ProductNo);
            if (product!=null && product.getTotal()>0){
                //根据mysql特性返回结果>0 修改成功
                int result=productMapper.updateTotal(product.getProduct_no());
                if (result>0) {
                    ProductRobbingRecord entity=new ProductRobbingRecord();
                    entity.setMobile(mobile);
                    entity.setProductId(product.getId());
                    //保存抢单记录
                    productRobbingRecordMapper.insertSelective(entity);
                }
            }
        }catch (Exception e){
            log.error("处理抢单发生异常：mobile={} ",mobile);
        }
    }



}

















