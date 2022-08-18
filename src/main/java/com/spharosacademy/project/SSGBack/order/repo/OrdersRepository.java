package com.spharosacademy.project.SSGBack.order.repo;

import com.spharosacademy.project.SSGBack.order.dto.request.OrdersOptionDto;
import com.spharosacademy.project.SSGBack.order.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

//    @Query(value = "select m from optionList m where m.colorId = :colorId, m.sizeId = :sizeId ")
//    List<OrdersOptionDto> findAllByColorIdAndsizeId(@Param("colorId")Long colorId,
//                                                    @Param("sizeId") Long sizeId);

}
