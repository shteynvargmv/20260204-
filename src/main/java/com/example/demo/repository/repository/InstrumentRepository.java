package com.example.demo.repository.repository;

import com.example.demo.entity.Instrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, String> {
//    @Query("SELECT i FROM Instrument i " +
//            "LEFT JOIN Bond b " +
//            "ON b.uid = i.bond.uid " +
//            "LEFT JOIN Share s " +
//            "ON s.uid = i.share.uid " +
//            "WHERE " +
//            "(:forQualInvestorFlag = false OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
//
//            "(((:sectors is null or b.sector IN :sectors) AND "  +
//            "(:amortizationFlag = false OR b.amortizationFlag != :amortizationFlag) AND " +
//            "(:noCallFlag = false OR b.noCallFlag = :noCallFlag) AND " +
//            "(:floatingCouponFlag = false OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
//            "(:perpetualFlag = false OR b.perpetualFlag = :perpetualFlag) AND " +
//            "(:subordinatedFlag = false OR b.subordinatedFlag = :subordinatedFlag) AND " +
//            "(:couponEveryMonthFlag = false OR b.couponEveryMonthFlag = :couponEveryMonthFlag)) OR " +
//
//            "((:sectors is null or s.sector IN :sectors) AND "  +
//            "(:divYieldFlag = false OR s.divYieldFlag = :divYieldFlag) AND " +
//            "(:liquidityFlag = false OR s.liquidityFlag = :liquidityFlag)))")
//    Page<Instrument> findAllBySectors(@Param("sectors") List<String> sectors,
//                                      @Param("amortizationFlag") Boolean amortizationFlag,
//                                      @Param("noCallFlag") Boolean noCallFlag,
//                                      @Param("floatingCouponFlag") Boolean floatingCouponFlag,
//                                      @Param("perpetualFlag") Boolean perpetualFlag,
//                                      @Param("subordinatedFlag") Boolean subordinatedFlag,
//                                      @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
//                                      @Param("divYieldFlag") Boolean divYieldFlag,
//                                      @Param("liquidityFlag") Boolean liquidityFlag,
//                                      @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
//                                      Pageable pageable);
//    @Query("SELECT i FROM Instrument i " +
//            "LEFT JOIN Bond b ON b.uid = i.bond.uid " +
//            "LEFT JOIN Share s ON s.uid = i.share.uid " +
//            "WHERE " +
//            "(:forQualInvestorFlag IS NULL OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
//            "(" +
//            "   (b IS NOT NULL AND " +
//            "    (:sectors IS NULL OR b.sector IN :sectors) AND " +
//            "    (:amortizationFlag IS NULL OR b.amortizationFlag != :amortizationFlag) AND " +
//            "    (:noCallFlag IS NULL OR b.noCallFlag = :noCallFlag) AND " +
//            "    (:floatingCouponFlag IS NULL OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
//            "    (:perpetualFlag IS NULL OR b.perpetualFlag = :perpetualFlag) AND " +
//            "    (:subordinatedFlag IS NULL OR b.subordinatedFlag = :subordinatedFlag) AND " +
//            "    (:couponEveryMonthFlag IS NULL OR b.couponEveryMonthFlag = :couponEveryMonthFlag)) " +
//            "   OR " +
//            "   (s IS NOT NULL AND " +
//            "    (:sectors IS NULL OR s.sector IN :sectors) AND " +
//            "    (:divYieldFlag IS NULL OR s.divYieldFlag = :divYieldFlag) AND " +
//            "    (:liquidityFlag IS NULL OR s.liquidityFlag = :liquidityFlag))" +
//            ")")
//    Page<Instrument> findAllBySectors(@Param("sectors") List<String> sectors,
//                                      @Param("amortizationFlag") Boolean amortizationFlag,
//                                      @Param("noCallFlag") Boolean noCallFlag,
//                                      @Param("floatingCouponFlag") Boolean floatingCouponFlag,
//                                      @Param("perpetualFlag") Boolean perpetualFlag,
//                                      @Param("subordinatedFlag") Boolean subordinatedFlag,
//                                      @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
//                                      @Param("divYieldFlag") Boolean divYieldFlag,
//                                      @Param("liquidityFlag") Boolean liquidityFlag,
//                                      @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
//                                      Pageable pageable);
//    @Query("SELECT i FROM Instrument i " +
//            "LEFT JOIN Bond b ON b.uid = i.bond.uid " +
//            "LEFT JOIN Share s ON s.uid = i.share.uid " +
//            "WHERE " +
//            "(:forQualInvestorFlag IS NULL OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
//            "(" +
//            "   (b IS NOT NULL AND " +
//            "    (:sectors IS NULL OR b.sector IN :sectors) AND " +
//            "    (:amortizationFlag IS NULL OR b.amortizationFlag != :amortizationFlag) AND " +
//            "    (:noCallFlag IS NULL OR b.noCallFlag = :noCallFlag) AND " +
//            "    (:floatingCouponFlag IS NULL OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
//            "    (:perpetualFlag IS NULL OR b.perpetualFlag = :perpetualFlag) AND " +
//            "    (:subordinatedFlag IS NULL OR b.subordinatedFlag = :subordinatedFlag) AND " +
//            "    (:couponEveryMonthFlag IS NULL OR b.couponEveryMonthFlag = :couponEveryMonthFlag)) " +
//            "   OR " +
//            "   (s IS NOT NULL AND " +
//            "    (:sectors IS NULL OR s.sector IN :sectors) AND " +
//            "    (:divYieldFlag IS NULL OR s.divYieldFlag = :divYieldFlag) AND " +
//            "    (:liquidityFlag IS NULL OR s.liquidityFlag = :liquidityFlag))" +
//            ")")
//    Page<Instrument> findAllBySectors(@Param("sectors") List<String> sectors,
//                                      @Param("amortizationFlag") Boolean amortizationFlag,
//                                      @Param("noCallFlag") Boolean noCallFlag,
//                                      @Param("floatingCouponFlag") Boolean floatingCouponFlag,
//                                      @Param("perpetualFlag") Boolean perpetualFlag,
//                                      @Param("subordinatedFlag") Boolean subordinatedFlag,
//                                      @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
//                                      @Param("divYieldFlag") Boolean divYieldFlag,
//                                      @Param("liquidityFlag") Boolean liquidityFlag,
//                                      @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
//                                      Pageable pageable);
//@Query("SELECT i FROM Instrument i " +
//        "LEFT JOIN Bond b ON b.uid = i.bond.uid " +
//        "LEFT JOIN Share s ON s.uid = i.share.uid " +
//        "LEFT JOIN Currency c ON c.uid = i.share.uid " +
//        "WHERE " +
//        "(:forQualInvestorFlag = false OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
//        "(" +
//        "   (b IS NOT NULL AND " +
//        "    (:sectors is null or b.sector IN :sectors) AND " +
//        "    (:amortizationFlag = false OR b.amortizationFlag != :amortizationFlag) AND " +
//        "    (:noCallFlag = false OR b.noCallFlag = :noCallFlag) AND " +
//        "    (:floatingCouponFlag = false OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
//        "    (:perpetualFlag = false OR b.perpetualFlag = :perpetualFlag) AND " +
//        "    (:subordinatedFlag = false OR b.subordinatedFlag = :subordinatedFlag) AND " +
//        "    (:couponEveryMonthFlag = false OR b.couponEveryMonthFlag = :couponEveryMonthFlag)) " +
//        "   OR " +
//        "   (s IS NOT NULL AND " +
//        "    (:sectors is null or s.sector IN :sectors) AND " +
//        "    (:divYieldFlag = false OR s.divYieldFlag = :divYieldFlag) AND " +
//        "    (:liquidityFlag = false OR s.liquidityFlag = :liquidityFlag))" +
//        "  OR " +
//        "    c IS NOT NULL" +
//        ")")
//Page<Instrument> findAllBySectors(@Param("sectors") List<String> sectors,
//                                @Param("amortizationFlag") Boolean amortizationFlag,
//                                @Param("noCallFlag") Boolean noCallFlag,
//                                @Param("floatingCouponFlag") Boolean floatingCouponFlag,
//                                @Param("perpetualFlag") Boolean perpetualFlag,
//                                @Param("subordinatedFlag") Boolean subordinatedFlag,
//                                @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
//                                @Param("divYieldFlag") Boolean divYieldFlag,
//                                @Param("liquidityFlag") Boolean liquidityFlag,
//                                @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
//                                Pageable pageable);

    @Query("SELECT i FROM Instrument i " +
            "WHERE " +
            "(:forQualInvestorFlag = false OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
            "( i.bond.uid IN ( SELECT b.uid FROM Bond b WHERE " +
            "    (:sectors is null or b.sector IN :sectors) AND " +
            "    (:amortizationFlag = false OR b.amortizationFlag != :amortizationFlag) AND " +
            "    (:noCallFlag = false OR b.noCallFlag = :noCallFlag) AND " +
            "    (:floatingCouponFlag = false OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
            "    (:perpetualFlag = false OR b.perpetualFlag = :perpetualFlag) AND " +
            "    (:subordinatedFlag = false OR b.subordinatedFlag = :subordinatedFlag) AND " +
            "    (:couponEveryMonthFlag = false OR b.couponEveryMonthFlag = :couponEveryMonthFlag)) " +
            "OR i.share.uid IN ( SELECT s.uid FROM Share s WHERE " +
            "    (:sectors is null or s.sector IN :sectors) AND " +
            "    (:divYieldFlag = false OR s.divYieldFlag = :divYieldFlag) AND " +
            "    (:liquidityFlag = false OR s.liquidityFlag = :liquidityFlag))" +
            "OR not i.currency.uid is null )")
    Page<Instrument> findAllBySectors(@Param("sectors") List<String> sectors,
                                      @Param("amortizationFlag") Boolean amortizationFlag,
                                      @Param("noCallFlag") Boolean noCallFlag,
                                      @Param("floatingCouponFlag") Boolean floatingCouponFlag,
                                      @Param("perpetualFlag") Boolean perpetualFlag,
                                      @Param("subordinatedFlag") Boolean subordinatedFlag,
                                      @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
                                      @Param("divYieldFlag") Boolean divYieldFlag,
                                      @Param("liquidityFlag") Boolean liquidityFlag,
                                      @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
                                      Pageable pageable);

    Page<Instrument> findAll(Pageable pageable);

    @Query("SELECT i FROM Instrument i " +
            "JOIN Bond b " +
            "ON b.uid = i.bond.uid " +
            "WHERE " +
            "(:forQualInvestorFlag = false OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
            "(:sectors is null or b.sector IN :sectors) AND " +
            "(:amortizationFlag = false OR b.amortizationFlag != :amortizationFlag) AND " +
            "(:noCallFlag = false OR b.noCallFlag = :noCallFlag) AND " +
            "(:floatingCouponFlag = false OR b.floatingCouponFlag != :floatingCouponFlag) AND " +
            "(:perpetualFlag = false OR b.perpetualFlag = :perpetualFlag) AND " +
            "(:subordinatedFlag = false OR b.subordinatedFlag = :subordinatedFlag) AND " +
            "(:couponEveryMonthFlag = false OR b.couponEveryMonthFlag = :couponEveryMonthFlag)" )
    Page<Instrument> findBondBySectors(@Param("sectors") List<String> sectors,
                                       @Param("amortizationFlag") Boolean amortizationFlag,
                                       @Param("noCallFlag") Boolean noCallFlag,
                                       @Param("floatingCouponFlag") Boolean floatingCouponFlag,
                                       @Param("perpetualFlag") Boolean perpetualFlag,
                                       @Param("subordinatedFlag") Boolean subordinatedFlag,
                                       @Param("couponEveryMonthFlag") Boolean couponEveryMonthFlag,
                                       @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
                                       Pageable pageable);

    Page<Instrument> findByBondIsNotNull(Pageable pageable);

    @Query("SELECT i FROM Instrument i " +
            "JOIN Share s " +
            "ON s.uid = i.share.uid " +
            "WHERE " +
            "(:forQualInvestorFlag = false OR i.forQualInvestorFlag != :forQualInvestorFlag) AND " +
            "(:sectors is null or s.sector IN :sectors) AND " +
            "(:divYieldFlag = false OR s.divYieldFlag = :divYieldFlag) AND " +
            "(:liquidityFlag = false OR s.liquidityFlag = :liquidityFlag)")
    Page<Instrument> findShareBySectors(@Param("sectors") List<String> sectors,
                                        @Param("divYieldFlag") boolean divYieldFlag,
                                        @Param("liquidityFlag") boolean liquidityFlag,
                                        @Param("forQualInvestorFlag") Boolean forQualInvestorFlag,
                                        Pageable pageable);

    Page<Instrument> findByShareIsNotNull(Pageable pageable);

    Page<Instrument> findByCurrencyIsNotNull(Pageable pageable);
}
