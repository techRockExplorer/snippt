/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rock.sell.dto.ReleaseSeatDTO
 *  com.rock.sell.dto.TicketLockDTO
 *  com.rock.sell.dto.TicketSoldDTO
 *  com.rock.sell.dto.TicketStubDTO
 *  com.rock.sell.entity.DealEntity
 *  com.rock.sell.entity.SeatForSaleEntity
 *  com.rock.sell.entity.SeatForSalePositionEntity
 *  com.rock.sell.entity.SeatLockEntity
 *  com.rock.sell.entity.SelectSeatForUpdateEntity
 *  com.rock.sell.entity.SellCounterfoilEntity
 *  com.rock.sell.exception.TicketException
 *  com.rock.sell.mapper.DealMapper
 *  com.rock.sell.mapper.PriceInfoMapper
 *  com.rock.sell.mapper.RunPlanMapper
 *  com.rock.sell.mapper.SeatForSaleMapper
 *  com.rock.sell.mapper.SeatLockMapper
 *  com.rock.sell.mapper.SellCounterfoilMapper
 *  com.rock.sell.mapper.StationStopSellTimeMapper
 *  com.rock.sell.mapper.SystemParameterMapper
 *  com.rock.sell.service.TicketService
 *  com.rock.sell.util.TransactionHelper
 *  com.rock.sell.vo.LockSeatResultVO
 *  com.rock.sell.vo.LockTicketVO
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 */
package com.rock.sell.service.impl;

import com.rock.sell.dto.ReleaseSeatDTO;
import com.rock.sell.dto.TicketLockDTO;
import com.rock.sell.dto.TicketSoldDTO;
import com.rock.sell.dto.TicketStubDTO;
import com.rock.sell.entity.DealEntity;
import com.rock.sell.entity.SeatForSaleEntity;
import com.rock.sell.entity.SeatForSalePositionEntity;
import com.rock.sell.entity.SeatLockEntity;
import com.rock.sell.entity.SelectSeatForUpdateEntity;
import com.rock.sell.entity.SellCounterfoilEntity;
import com.rock.sell.exception.TicketException;
import com.rock.sell.mapper.DealMapper;
import com.rock.sell.mapper.PriceInfoMapper;
import com.rock.sell.mapper.RunPlanMapper;
import com.rock.sell.mapper.SeatForSaleMapper;
import com.rock.sell.mapper.SeatLockMapper;
import com.rock.sell.mapper.SellCounterfoilMapper;
import com.rock.sell.mapper.StationStopSellTimeMapper;
import com.rock.sell.mapper.SystemParameterMapper;
import com.rock.sell.service.TicketService;
import com.rock.sell.util.TransactionHelper;
import com.rock.sell.vo.LockSeatResultVO;
import com.rock.sell.vo.LockTicketVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\b\u0017\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0012J\u0018\u0010.\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010/2\u0006\u00101\u001a\u00020-H\u0012J\u0018\u00102\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010/2\u0006\u00101\u001a\u00020-H\u0012J\u001e\u00103\u001a\u00020+2\u0006\u00101\u001a\u00020-2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050/H\u0012J\u0010\u00106\u001a\u0002072\u0006\u00101\u001a\u00020-H\u0017J(\u00108\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010/2\u0006\u00101\u001a\u00020-2\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020:H\u0012J\u0016\u0010<\u001a\b\u0012\u0004\u0012\u0002050/2\u0006\u00101\u001a\u00020-H\u0012J\u0010\u0010=\u001a\u00020+2\u0006\u0010>\u001a\u00020?H\u0012J\u0010\u0010@\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0017J\u0010\u0010C\u001a\u00020+2\u0006\u0010A\u001a\u00020DH\u0017J\u0010\u0010E\u001a\u00020+2\u0006\u0010>\u001a\u00020?H\u0012J@\u0010F\u001a\u00020+2\u0006\u0010G\u001a\u0002002\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020:2\u0006\u0010K\u001a\u00020I2\u0006\u0010L\u001a\u00020:2\u0006\u0010M\u001a\u00020I2\u0006\u0010N\u001a\u00020:H\u0012J\u0016\u0010O\u001a\u00020+2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050/H\u0012J\u0018\u0010P\u001a\u00020+2\u0006\u0010G\u001a\u0002002\u0006\u0010Q\u001a\u00020:H\u0012R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\n \u0019*\u0004\u0018\u00010\u00180\u0018X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)\u00a8\u0006R"}, d2={"Lcom/rock/sell/service/impl/TicketServiceImpl;", "Lcom/rock/sell/service/TicketService;", "stationStopSellTimeMapper", "Lcom/rock/sell/mapper/StationStopSellTimeMapper;", "runPlanMapper", "Lcom/rock/sell/mapper/RunPlanMapper;", "seatForSaleMapper", "Lcom/rock/sell/mapper/SeatForSaleMapper;", "systemParameterMapper", "Lcom/rock/sell/mapper/SystemParameterMapper;", "priceInfoMapper", "Lcom/rock/sell/mapper/PriceInfoMapper;", "transactionHelper", "Lcom/rock/sell/util/TransactionHelper;", "seatLockMapper", "Lcom/rock/sell/mapper/SeatLockMapper;", "sellCounterfoilMapper", "Lcom/rock/sell/mapper/SellCounterfoilMapper;", "dealMapper", "Lcom/rock/sell/mapper/DealMapper;", "(Lcom/rock/sell/mapper/StationStopSellTimeMapper;Lcom/rock/sell/mapper/RunPlanMapper;Lcom/rock/sell/mapper/SeatForSaleMapper;Lcom/rock/sell/mapper/SystemParameterMapper;Lcom/rock/sell/mapper/PriceInfoMapper;Lcom/rock/sell/util/TransactionHelper;Lcom/rock/sell/mapper/SeatLockMapper;Lcom/rock/sell/mapper/SellCounterfoilMapper;Lcom/rock/sell/mapper/DealMapper;)V", "getDealMapper", "()Lcom/rock/sell/mapper/DealMapper;", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "getPriceInfoMapper", "()Lcom/rock/sell/mapper/PriceInfoMapper;", "getRunPlanMapper", "()Lcom/rock/sell/mapper/RunPlanMapper;", "getSeatForSaleMapper", "()Lcom/rock/sell/mapper/SeatForSaleMapper;", "getSeatLockMapper", "()Lcom/rock/sell/mapper/SeatLockMapper;", "getSellCounterfoilMapper", "()Lcom/rock/sell/mapper/SellCounterfoilMapper;", "getStationStopSellTimeMapper", "()Lcom/rock/sell/mapper/StationStopSellTimeMapper;", "getSystemParameterMapper", "()Lcom/rock/sell/mapper/SystemParameterMapper;", "getTransactionHelper", "()Lcom/rock/sell/util/TransactionHelper;", "checkWhetherIsBeforeClose", "", "request", "Lcom/rock/sell/dto/TicketLockDTO;", "doQueryContinuousUnlockedSeats", "", "", "info", "doQueryRandomUnlockedSeats", "insertSeatLockRecord", "tickets", "Lcom/rock/sell/vo/LockTicketVO;", "lock", "Lcom/rock/sell/vo/LockSeatResultVO;", "queryContinuousUnlockedSeatsWithType", "type", "", "reused", "queryUnlockedSeats", "refreshReuseSeat", "stub", "Lcom/rock/sell/dto/TicketStubDTO;", "releaseSeat", "dto", "Lcom/rock/sell/dto/ReleaseSeatDTO;", "saveSoldStubs", "Lcom/rock/sell/dto/TicketSoldDTO;", "updateDepartAndArriveStation", "updateSeatReuseState", "seatCode", "preSta", "", "preStaSeq", "beyondSta", "beyondStaSeq", "arriveStation", "arrStationSeq", "updateSeatStatus", "updateSeatStatusToSold", "operateWindow", "sell-service"})
@SourceDebugExtension(value={"SMAP\nTicketServiceImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TicketServiceImpl.kt\ncom/rock/sell/service/impl/TicketServiceImpl\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,798:1\n1549#2:799\n1620#2,3:800\n*S KotlinDebug\n*F\n+ 1 TicketServiceImpl.kt\ncom/rock/sell/service/impl/TicketServiceImpl\n*L\n65#1:799\n65#1:800,3\n*E\n"})
public class TicketServiceImpl
implements TicketService {
    @NotNull
    private final StationStopSellTimeMapper stationStopSellTimeMapper;
    @NotNull
    private final RunPlanMapper runPlanMapper;
    @NotNull
    private final SeatForSaleMapper seatForSaleMapper;
    @NotNull
    private final SystemParameterMapper systemParameterMapper;
    @NotNull
    private final PriceInfoMapper priceInfoMapper;
    @NotNull
    private final TransactionHelper transactionHelper;
    @NotNull
    private final SeatLockMapper seatLockMapper;
    @NotNull
    private final SellCounterfoilMapper sellCounterfoilMapper;
    @NotNull
    private final DealMapper dealMapper;
    private final Logger logger;

    public TicketServiceImpl(@NotNull StationStopSellTimeMapper stationStopSellTimeMapper, @NotNull RunPlanMapper runPlanMapper, @NotNull SeatForSaleMapper seatForSaleMapper, @NotNull SystemParameterMapper systemParameterMapper, @NotNull PriceInfoMapper priceInfoMapper, @NotNull TransactionHelper transactionHelper, @NotNull SeatLockMapper seatLockMapper, @NotNull SellCounterfoilMapper sellCounterfoilMapper, @NotNull DealMapper dealMapper) {
        Intrinsics.checkNotNullParameter((Object)stationStopSellTimeMapper, (String)"stationStopSellTimeMapper");
        Intrinsics.checkNotNullParameter((Object)runPlanMapper, (String)"runPlanMapper");
        Intrinsics.checkNotNullParameter((Object)seatForSaleMapper, (String)"seatForSaleMapper");
        Intrinsics.checkNotNullParameter((Object)systemParameterMapper, (String)"systemParameterMapper");
        Intrinsics.checkNotNullParameter((Object)priceInfoMapper, (String)"priceInfoMapper");
        Intrinsics.checkNotNullParameter((Object)transactionHelper, (String)"transactionHelper");
        Intrinsics.checkNotNullParameter((Object)seatLockMapper, (String)"seatLockMapper");
        Intrinsics.checkNotNullParameter((Object)sellCounterfoilMapper, (String)"sellCounterfoilMapper");
        Intrinsics.checkNotNullParameter((Object)dealMapper, (String)"dealMapper");
        this.stationStopSellTimeMapper = stationStopSellTimeMapper;
        this.runPlanMapper = runPlanMapper;
        this.seatForSaleMapper = seatForSaleMapper;
        this.systemParameterMapper = systemParameterMapper;
        this.priceInfoMapper = priceInfoMapper;
        this.transactionHelper = transactionHelper;
        this.seatLockMapper = seatLockMapper;
        this.sellCounterfoilMapper = sellCounterfoilMapper;
        this.dealMapper = dealMapper;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @NotNull
    public StationStopSellTimeMapper getStationStopSellTimeMapper() {
        return this.stationStopSellTimeMapper;
    }

    @NotNull
    public RunPlanMapper getRunPlanMapper() {
        return this.runPlanMapper;
    }

    @NotNull
    public SeatForSaleMapper getSeatForSaleMapper() {
        return this.seatForSaleMapper;
    }

    @NotNull
    public SystemParameterMapper getSystemParameterMapper() {
        return this.systemParameterMapper;
    }

    @NotNull
    public PriceInfoMapper getPriceInfoMapper() {
        return this.priceInfoMapper;
    }

    @NotNull
    public TransactionHelper getTransactionHelper() {
        return this.transactionHelper;
    }

    @NotNull
    public SeatLockMapper getSeatLockMapper() {
        return this.seatLockMapper;
    }

    @NotNull
    public SellCounterfoilMapper getSellCounterfoilMapper() {
        return this.sellCounterfoilMapper;
    }

    @NotNull
    public DealMapper getDealMapper() {
        return this.dealMapper;
    }

    @Transactional
    @NotNull
    public LockSeatResultVO lock(@NotNull TicketLockDTO info) {
        Intrinsics.checkNotNullParameter((Object)info, (String)"info");
        this.checkWhetherIsBeforeClose(info);
        List<LockTicketVO> tickets = this.queryUnlockedSeats(info);
        this.updateSeatStatus(tickets);
        this.insertSeatLockRecord(info, tickets);
        return new LockSeatResultVO(info.getBoardingTime(), info.getArriveTime(), info.getArriveStationName(), info.getDepartStationName(), info.getTrainId(), info.getTrainNum(), tickets);
    }

    /*
     * WARNING - void declaration
     */
    private void insertSeatLockRecord(TicketLockDTO info, List<LockTicketVO> tickets) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = tickets;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void ticket;
            LockTicketVO lockTicketVO = (LockTicketVO)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new SeatLockEntity(null, Long.valueOf(ticket.getSeatCode()), info.getOperationStation(), Integer.valueOf(info.getOperationWindowId()), Long.valueOf(22282L), LocalDateTime.now(), Integer.valueOf(1)));
        }
        List records = (List)destination$iv$iv;
        int insertCount = this.getSeatLockMapper().insert(records);
        this.logger.info("insert seat lock record: {}", (Object)insertCount);
    }

    private void updateSeatStatus(List<LockTicketVO> tickets) {
        SeatForSaleMapper seatForSaleMapper = this.getSeatForSaleMapper();
        LocalDateTime localDateTime = LocalDateTime.now();
        Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
        int updateCount = seatForSaleMapper.updateSeatStatus(tickets, 3, localDateTime);
        this.logger.info("update seat status to locked: {}", (Object)updateCount);
    }

    private List<LockTicketVO> queryUnlockedSeats(TicketLockDTO info) {
        List<Long> seats;
        Integer n;
        Object[] objectArray = this.getSystemParameterMapper().selectByPrimaryKey(Integer.valueOf(1));
        int lockSeatMode = objectArray != null && (n = objectArray.getParamValue()) != null ? n : 2;
        objectArray = new Object[]{lockSeatMode, 2, 1};
        this.logger.info("lock seat mode={} (continuous={},random={})", objectArray);
        List<Long> list = seats = lockSeatMode == 1 ? this.doQueryRandomUnlockedSeats(info) : this.doQueryContinuousUnlockedSeats(info);
        if (seats == null || seats.size() != info.getSeatCount()) {
            throw new TicketException("no enough seats");
        }
        List seatsInfo = this.getSeatForSaleMapper().selectSeatsForUpdate(seats);
        for (SelectSeatForUpdateEntity seat : seatsInfo) {
            if (seat.getStatus() == 2) continue;
            this.logger.warn("seat[{}] status is {}, not unsold, rollback", (Object)seat.getSeatCode(), (Object)seat.getStatus());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            Thread.sleep(10L);
            this.getTransactionHelper().withTransaction(() -> TicketServiceImpl.queryUnlockedSeats$lambda$2(this, info));
            break;
        }
        this.logger.info("lock seats: {}", (Object)seatsInfo);
        String string = this.getRunPlanMapper().selectPriceName(info.getTrainId());
        if (string == null) {
            throw new TicketException("no price name for train[" + info.getTrainId());
        }
        String priceName = string;
        List result = new ArrayList();
        for (SelectSeatForUpdateEntity seat : seatsInfo) {
            Double d = this.getPriceInfoMapper().selectPrice(priceName, info.getDepartStationName(), info.getArriveStationName(), info.getSeatType(), seat.getBerthType(), info.getTicketType());
            if (d == null) {
                throw new TicketException("no price of seatType=" + info.getSeatType() + " depSta=" + info.getDepartStationName() + " arrSta=" + info.getArriveStationName() + " berthType=" + seat.getBerthType() + " ticketType=" + info.getTicketType() + "for train[" + info.getTrainId() + "]");
            }
            double price = d;
            LockTicketVO ticket = new LockTicketVO(seat.getSeatCode(), seat.getSeatNo(), seat.getCarNo(), info.getSeatType(), seat.getBerthType(), info.getTicketType(), price);
            result.add(ticket);
        }
        return result;
    }

    @Transactional
    public void releaseSeat(@NotNull ReleaseSeatDTO dto) {
        Intrinsics.checkNotNullParameter((Object)dto, (String)"dto");
        Iterator iterator = dto.getSeatCodes().iterator();
        while (iterator.hasNext()) {
            long seatCode = ((Number)iterator.next()).longValue();
            SeatForSaleMapper seatForSaleMapper = this.getSeatForSaleMapper();
            LocalDateTime localDateTime = LocalDateTime.now();
            Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
            int updatedCount = seatForSaleMapper.updateStatusAndChangeTime(3, 2, seatCode, localDateTime);
            if (updatedCount > 1) {
                throw new TicketException("updated seat " + seatCode + " more than one");
            }
            if (updatedCount == 0) {
                throw new TicketException("status of seat " + seatCode + " is not locked");
            }
            this.logger.info("release seat {} successfully", (Object)seatCode);
            List seatLockRecords = this.getSeatLockMapper().selectBySeatCode(seatCode);
            this.logger.info("seat lock records before release: {}", (Object)seatLockRecords);
            SeatLockEntity seatLockEntity = new SeatLockEntity(null, Long.valueOf(seatCode), dto.getStationName(), Integer.valueOf(dto.getWindow()), dto.getStaffId(), LocalDateTime.now(), Integer.valueOf(2));
            this.getSeatLockMapper().insert(CollectionsKt.listOf((Object)seatLockEntity));
        }
    }

    @Transactional
    public void saveSoldStubs(@NotNull TicketSoldDTO dto) {
        Intrinsics.checkNotNullParameter((Object)dto, (String)"dto");
        DealEntity dealEntity = new DealEntity(null, 1, dto.getOperateStation(), dto.getOperateWindow(), dto.getStuffId(), dto.getOperationTime(), dto.getTotalPrice(), dto.getPayType(), null, Double.valueOf(dto.getTicketPriceByCard()), dto.getTransactionState(), dto.getUserAccount(), dto.getWinAccount(), null, null, dto.getPayType() == 4 ? "Haven't pay" : "Paid by Cash or Pos Machine", LocalDateTime.now());
        this.getDealMapper().insert(dealEntity);
        this.logger.info("deal saved: {}", (Object)dealEntity);
        for (TicketStubDTO stub : dto.getStubs()) {
            this.updateSeatStatusToSold(stub.getSeatCode(), dto.getOperateWindow());
            this.refreshReuseSeat(stub);
            this.updateDepartAndArriveStation(stub);
            long l = stub.getSeatCode();
            Long l2 = dealEntity.getDealNo();
            Intrinsics.checkNotNull((Object)l2);
            long l3 = l2;
            long l4 = stub.getTrainID();
            String string = stub.getTrainNum();
            LocalDate localDate = stub.getAboardTime().toLocalDate();
            Intrinsics.checkNotNullExpressionValue((Object)localDate, (String)"stub.aboardTime.toLocalDate()");
            SellCounterfoilEntity counterfoilEntity = new SellCounterfoilEntity(null, l, l3, l4, string, localDate, stub.getAboardTime(), stub.getDepartStation(), stub.getArriveStation(), stub.getQrCode(), stub.getSeatType(), stub.getCarriageNum(), stub.getSeatNo(), stub.getTicketType(), stub.getTicketPrice(), stub.getAccountNum(), stub.getSoldType(), 1, Integer.valueOf(dto.getOperateWindow()), stub.getBerthType(), Integer.valueOf(stub.getPrintState()), stub.getId(), stub.getName(), stub.getGender(), stub.getNationality());
            this.getSellCounterfoilMapper().insert(counterfoilEntity);
            this.logger.info("counterfoil saved: {}", (Object)counterfoilEntity);
        }
    }

    private void updateDepartAndArriveStation(TicketStubDTO stub) {
        String departStation = stub.getDepartStation();
        String arriveStation = stub.getArriveStation();
        Integer depStationSeq = this.getRunPlanMapper().selectStationSequence(stub.getTrainID(), departStation);
        Integer arrStationSeq = this.getRunPlanMapper().selectStationSequence(stub.getTrainID(), arriveStation);
        SeatForSaleMapper seatForSaleMapper = this.getSeatForSaleMapper();
        long l = stub.getSeatCode();
        Integer n = depStationSeq;
        Intrinsics.checkNotNull((Object)n);
        int n2 = n;
        Integer n3 = arrStationSeq;
        Intrinsics.checkNotNull((Object)n3);
        int updateCount = seatForSaleMapper.updateAboardAndSellArriveStation(l, departStation, n2, arriveStation, n3.intValue());
        if (updateCount != 1) {
            this.logger.warn("update seat[{}] depart and arrive station failed", (Object)stub.getSeatCode());
            throw new TicketException("update seat[" + stub.getSeatCode() + "] depart and arrive station failed, cannot sell");
        }
    }

    private void refreshReuseSeat(TicketStubDTO stub) {
        long trainId = stub.getTrainID();
        long seatCode = stub.getSeatCode();
        String departStation = stub.getDepartStation();
        String arriveStation = stub.getArriveStation();
        Integer depStationSeq = this.getRunPlanMapper().selectStationSequence(trainId, departStation);
        Integer arrStationSeq = this.getRunPlanMapper().selectStationSequence(trainId, arriveStation);
        if (depStationSeq == null || depStationSeq == 0) {
            throw new TicketException("no depart station sequence of train[" + trainId + "]");
        }
        if (arrStationSeq == null || arrStationSeq == 0) {
            throw new TicketException("no arrive station sequence of train[" + trainId + "]");
        }
        SeatForSaleEntity seatForSaleEntity = this.getSeatForSaleMapper().selectByPrimaryKey(seatCode);
        if (seatForSaleEntity == null) {
            throw new TicketException("seat[" + seatCode + "] not found");
        }
        SeatForSaleEntity seatToUpdate = seatForSaleEntity;
        if (Intrinsics.areEqual((Object)seatToUpdate.getPreSta(), (Object)departStation) && !Intrinsics.areEqual((Object)seatToUpdate.getLimitedSta(), (Object)arriveStation)) {
            long l = seatToUpdate.getTrainId();
            String string = seatToUpdate.getSeatType();
            int n = seatToUpdate.getCarNo();
            String string2 = seatToUpdate.getSeatNo();
            String string3 = seatToUpdate.getBerthType();
            int n2 = seatToUpdate.getRowNo();
            int n3 = seatToUpdate.getColumnNo();
            int n4 = seatToUpdate.getSeqNo();
            int n5 = seatToUpdate.getFeature();
            int n6 = arrStationSeq;
            int n7 = arrStationSeq;
            String string4 = seatToUpdate.getLimitedSta();
            int n8 = seatToUpdate.getLimitedStaSeq();
            Integer n9 = 0;
            Integer n10 = 0;
            String string5 = seatToUpdate.getPurpose();
            LocalDateTime localDateTime = LocalDateTime.now();
            Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
            SeatForSaleEntity newSeat = new SeatForSaleEntity(null, l, string, n, string2, string3, n2, n3, n4, n5, arriveStation, n6, arriveStation, n7, string4, n8, "", n9, "", n10, string5, 2, localDateTime, seatToUpdate.getSellMode(), 1, seatToUpdate.getValidIdt());
            this.logger.info("add reused new seat: {}", (Object)newSeat);
            Long l2 = seatToUpdate.getSeatCode();
            Intrinsics.checkNotNull((Object)l2);
            this.updateSeatReuseState(l2, seatToUpdate.getPreSta(), seatToUpdate.getPreStaSeq(), seatToUpdate.getBeyondSta(), seatToUpdate.getBeyondStaSeq(), stub.getArriveStation(), arrStationSeq);
        } else if (!Intrinsics.areEqual((Object)seatToUpdate.getPreSta(), (Object)stub.getDepartStation()) && Intrinsics.areEqual((Object)seatToUpdate.getLimitedSta(), (Object)stub.getArriveStation())) {
            String newBeyondSta = seatToUpdate.getBeyondSta();
            int newBeyondStaSeq = seatToUpdate.getBeyondStaSeq();
            String oldBeyondSta = seatToUpdate.getBeyondSta();
            int oldBeyondStaSeq = seatToUpdate.getBeyondStaSeq();
            if (depStationSeq < seatToUpdate.getBeyondStaSeq()) {
                newBeyondStaSeq = seatToUpdate.getPreStaSeq();
                newBeyondSta = seatToUpdate.getPreSta();
            } else if (depStationSeq > seatToUpdate.getBeyondStaSeq()) {
                oldBeyondSta = stub.getDepartStation();
                oldBeyondStaSeq = depStationSeq;
            }
            long l = seatToUpdate.getTrainId();
            String string = seatToUpdate.getSeatType();
            int n = seatToUpdate.getCarNo();
            String string6 = seatToUpdate.getSeatNo();
            String string7 = seatToUpdate.getBerthType();
            int n11 = seatToUpdate.getRowNo();
            int n12 = seatToUpdate.getColumnNo();
            int n13 = seatToUpdate.getSeqNo();
            int n14 = seatToUpdate.getFeature();
            String string8 = seatToUpdate.getPreSta();
            int n15 = seatToUpdate.getPreStaSeq();
            String string9 = stub.getDepartStation();
            int n16 = depStationSeq;
            Integer n17 = 0;
            Integer n18 = 0;
            String string10 = seatToUpdate.getPurpose();
            LocalDateTime localDateTime = LocalDateTime.now();
            Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
            SeatForSaleEntity newSeat = new SeatForSaleEntity(null, l, string, n, string6, string7, n11, n12, n13, n14, string8, n15, newBeyondSta, newBeyondStaSeq, string9, n16, "", n17, "", n18, string10, 2, localDateTime, seatToUpdate.getSellMode(), 1, seatToUpdate.getValidIdt());
            this.logger.info("add reused new seat: {}", (Object)newSeat);
            Long l3 = seatToUpdate.getSeatCode();
            Intrinsics.checkNotNull((Object)l3);
            this.updateSeatReuseState(l3, stub.getDepartStation(), depStationSeq, oldBeyondSta, oldBeyondStaSeq, seatToUpdate.getLimitedSta(), seatToUpdate.getLimitedStaSeq());
        } else if (!Intrinsics.areEqual((Object)seatToUpdate.getPreSta(), (Object)stub.getDepartStation()) && !Intrinsics.areEqual((Object)seatToUpdate.getLimitedSta(), (Object)stub.getDepartStation())) {
            String strNewForwardBeyondSta = "";
            String strNewBackForwardBeyondSta = "";
            String strOldBeyondSta = "";
            int uNewForwardBeyond = 0;
            int uNewBackForwardBeyond = 0;
            int uOldBeyondSta = 0;
            if (seatToUpdate.getBeyondStaSeq() > depStationSeq && seatToUpdate.getBeyondStaSeq() < arrStationSeq) {
                strNewForwardBeyondSta = stub.getDepartStation();
                strNewBackForwardBeyondSta = stub.getArriveStation();
                strOldBeyondSta = seatToUpdate.getBeyondSta();
                uNewForwardBeyond = seatToUpdate.getBeyondStaSeq();
                uNewBackForwardBeyond = arrStationSeq;
                uOldBeyondSta = seatToUpdate.getBeyondStaSeq();
            } else if (seatToUpdate.getBeyondStaSeq() < depStationSeq) {
                strNewForwardBeyondSta = seatToUpdate.getBeyondSta();
                strNewBackForwardBeyondSta = stub.getArriveStation();
                strOldBeyondSta = stub.getDepartStation();
                uNewForwardBeyond = seatToUpdate.getBeyondStaSeq();
                uNewBackForwardBeyond = arrStationSeq;
                uOldBeyondSta = depStationSeq;
            } else if (seatToUpdate.getBeyondStaSeq() >= arrStationSeq) {
                strNewForwardBeyondSta = seatToUpdate.getPreSta();
                strNewBackForwardBeyondSta = seatToUpdate.getBeyondSta();
                strOldBeyondSta = stub.getDepartStation();
                uNewForwardBeyond = seatToUpdate.getPreStaSeq();
                uNewBackForwardBeyond = seatToUpdate.getBeyondStaSeq();
                uOldBeyondSta = depStationSeq;
            } else if (seatToUpdate.getBeyondStaSeq() == depStationSeq.intValue()) {
                strNewForwardBeyondSta = seatToUpdate.getPreSta();
                strNewBackForwardBeyondSta = stub.getArriveStation();
                strOldBeyondSta = seatToUpdate.getBeyondSta();
                uNewBackForwardBeyond = arrStationSeq;
                uNewForwardBeyond = seatToUpdate.getPreStaSeq();
                uOldBeyondSta = seatToUpdate.getBeyondStaSeq();
            }
            long l = seatToUpdate.getTrainId();
            String string = seatToUpdate.getSeatType();
            int n = seatToUpdate.getCarNo();
            String string11 = seatToUpdate.getSeatNo();
            String string12 = seatToUpdate.getBerthType();
            int n19 = seatToUpdate.getRowNo();
            int n20 = seatToUpdate.getColumnNo();
            int n21 = seatToUpdate.getSeqNo();
            int n22 = seatToUpdate.getFeature();
            String string13 = seatToUpdate.getPreSta();
            int n23 = seatToUpdate.getPreStaSeq();
            String string14 = stub.getDepartStation();
            int n24 = depStationSeq;
            Integer n25 = 0;
            Integer n26 = 0;
            String string15 = seatToUpdate.getPurpose();
            LocalDateTime localDateTime = LocalDateTime.now();
            Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
            SeatForSaleEntity newSeat1 = new SeatForSaleEntity(null, l, string, n, string11, string12, n19, n20, n21, n22, string13, n23, strNewForwardBeyondSta, uNewForwardBeyond, string14, n24, "", n25, "", n26, string15, 2, localDateTime, seatToUpdate.getSellMode(), 1, seatToUpdate.getValidIdt());
            this.getSeatForSaleMapper().insert(newSeat1);
            this.logger.info("add reused new seat1: {}", (Object)newSeat1);
            long l4 = seatToUpdate.getTrainId();
            String string16 = seatToUpdate.getSeatType();
            int n27 = seatToUpdate.getCarNo();
            String string17 = seatToUpdate.getSeatNo();
            String string18 = seatToUpdate.getBerthType();
            int n28 = seatToUpdate.getRowNo();
            int n29 = seatToUpdate.getColumnNo();
            int n30 = seatToUpdate.getSeqNo();
            int n31 = seatToUpdate.getFeature();
            String string19 = stub.getArriveStation();
            int n32 = arrStationSeq;
            String string20 = seatToUpdate.getLimitedSta();
            int n33 = seatToUpdate.getLimitedStaSeq();
            Integer n34 = 0;
            Integer n35 = 0;
            String string21 = seatToUpdate.getPurpose();
            LocalDateTime localDateTime2 = LocalDateTime.now();
            Intrinsics.checkNotNullExpressionValue((Object)localDateTime2, (String)"now()");
            SeatForSaleEntity newSeat2 = new SeatForSaleEntity(null, l4, string16, n27, string17, string18, n28, n29, n30, n31, string19, n32, strNewBackForwardBeyondSta, uNewBackForwardBeyond, string20, n33, "", n34, "", n35, string21, 2, localDateTime2, seatToUpdate.getSellMode(), 1, seatToUpdate.getValidIdt());
            this.getSeatForSaleMapper().insert(newSeat2);
            this.logger.info("add reused new seat2: {}", (Object)newSeat2);
            this.updateSeatReuseState(stub.getSeatCode(), stub.getDepartStation(), depStationSeq, strOldBeyondSta, uOldBeyondSta, stub.getArriveStation(), arrStationSeq);
        } else if (Intrinsics.areEqual((Object)seatToUpdate.getPreSta(), (Object)stub.getDepartStation()) && Intrinsics.areEqual((Object)seatToUpdate.getLimitedSta(), (Object)stub.getArriveStation())) {
            this.logger.info("do nothing for seat[{}] when add new reuse seat", (Object)seatToUpdate.getSeatCode());
        } else {
            throw new TicketException("invalid seat[" + seatToUpdate.getSeatCode() + "]");
        }
    }

    private void updateSeatReuseState(long seatCode, String preSta, int preStaSeq, String beyondSta, int beyondStaSeq, String arriveStation, int arrStationSeq) {
        int updatedCount = this.getSeatForSaleMapper().updateSeatReuseState(seatCode, preSta, preStaSeq, beyondSta, beyondStaSeq, arriveStation, arrStationSeq);
        if (updatedCount != 1) {
            this.logger.warn("update seat[{}] reuse state failed", (Object)seatCode);
            throw new TicketException("update seat[" + seatCode + "] reuse state failed");
        }
        this.logger.info("update seat[" + seatCode + "] reuse state successfully");
    }

    private void updateSeatStatusToSold(long seatCode, int operateWindow) {
        Integer winCode;
        Integer n = winCode = this.getSeatLockMapper().selectWinCodeBySeatCode(seatCode);
        int n2 = operateWindow;
        if (n == null || n != n2) {
            throw new TicketException("seat[" + seatCode + "] is locked by window[" + winCode + "], not " + operateWindow + ", can not sell");
        }
        SeatForSaleMapper seatForSaleMapper = this.getSeatForSaleMapper();
        LocalDateTime localDateTime = LocalDateTime.now();
        Intrinsics.checkNotNullExpressionValue((Object)localDateTime, (String)"now()");
        int updatedCount = seatForSaleMapper.updateStatusAndChangeTime(3, 4, seatCode, localDateTime);
        if (updatedCount != 1) {
            SeatForSaleEntity seatForSaleEntity = this.getSeatForSaleMapper().selectByPrimaryKey(seatCode);
            SeatForSaleEntity seatForSaleEntity2 = seatForSaleEntity;
            this.logger.warn("seat[{}] status is {}, not locked, rollback", (Object)seatCode, (Object)(seatForSaleEntity2 != null ? Integer.valueOf(seatForSaleEntity2.getStatus()) : null));
            throw new TicketException("seat[" + seatCode + "]'s status is " + seatForSaleEntity + "?.status, not locked, can not sale");
        }
        this.logger.info("update seat {} to sold successfully", (Object)seatCode);
    }

    private List<Long> doQueryContinuousUnlockedSeats(TicketLockDTO info) {
        long trainId = info.getTrainId();
        int depSeq = info.getDepartureStaSeq();
        String depSta = info.getDepartStationName();
        int arrSeq = info.getArriveStaSeq();
        String arrSta = info.getArriveStationName();
        int seatCount = info.getSeatCount();
        String seatType = info.getSeatType();
        String purpose = info.getPurpose();
        String operationSta = info.getOperationStation();
        if (!Intrinsics.areEqual((Object)operationSta, (Object)depSta)) {
            // empty if block
        }
        Integer carNo = this.getSeatForSaleMapper().selectRandomCar(trainId, seatType, purpose, 1, 1, depSeq, arrSeq, 2, seatCount, 1);
        return null;
    }

    private List<Long> doQueryRandomUnlockedSeats(TicketLockDTO info) {
        List<Long> position = this.queryContinuousUnlockedSeatsWithType(info, 1, 1);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 2, 1);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 3, 1);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 4, 1);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 1, 0);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 2, 0);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 3, 0);
        if (position != null) {
            return position;
        }
        position = this.queryContinuousUnlockedSeatsWithType(info, 4, 0);
        return position;
    }

    private List<Long> queryContinuousUnlockedSeatsWithType(TicketLockDTO info, int type, int reused) {
        long trainId = info.getTrainId();
        int depSeq = info.getDepartureStaSeq();
        String depSta = info.getDepartStationName();
        int arrSeq = info.getArriveStaSeq();
        String arrSta = info.getArriveStationName();
        int seatCount = info.getSeatCount();
        String seatType = info.getSeatType();
        String purpose = info.getPurpose();
        String operationSta = info.getOperationStation();
        Integer carNo = this.getSeatForSaleMapper().selectRandomCar(trainId, seatType, purpose, 1, reused, depSeq, arrSeq, 2, seatCount, type);
        if (carNo == null) {
            return null;
        }
        SeatForSalePositionEntity position = this.getSeatForSaleMapper().selectUnlockedSeats(info.getTrainId(), info.getSeatType(), info.getPurpose(), 1, reused, info.getDepartureStaSeq(), info.getArriveStaSeq(), 2, info.getSeatCount(), 1, carNo.intValue());
        if (position == null) {
            return null;
        }
        Integer n = position.getBeginNo();
        List seatCodes = this.getSeatForSaleMapper().selectSeatCodeByCarNoAndBeginSeat(info.getTrainId(), info.getSeatType(), info.getPurpose(), 1, reused, info.getDepartureStaSeq(), info.getArriveStaSeq(), 2, info.getSeatCount(), 1, carNo.intValue(), n != null ? n : 0);
        return seatCodes;
    }

    private void checkWhetherIsBeforeClose(TicketLockDTO request) {
        int stopBeforeDeparture;
        String depStation = request.getDepartStationName();
        Object object = this.getStationStopSellTimeMapper().selectFirstByStation(depStation);
        int n = stopBeforeDeparture = object != null && (object = object.getStopTime()) != null ? (Integer)object : 10;
        LocalDateTime localDateTime = this.getRunPlanMapper().selectDepTimeByTrainIdAndDepStation(depStation, request.getTrainId());
        if (localDateTime == null) {
            localDateTime = LocalDateTime.MAX;
        }
        LocalDateTime depTime = localDateTime;
        LocalDateTime now = LocalDateTime.now();
        if (depTime.minusMinutes(stopBeforeDeparture).isBefore(now)) {
            throw new TicketException("Ticket sales for this train[" + request.getTrainId() + "] have already stopped (departure time: " + depTime + ", ticket sales end " + stopBeforeDeparture + " minutes prior");
        }
    }

    private static final LockSeatResultVO queryUnlockedSeats$lambda$2$lambda$1(TicketServiceImpl this$0, TicketLockDTO $info) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$info, (String)"$info");
        return this$0.lock($info);
    }

    private static final void queryUnlockedSeats$lambda$2(TicketServiceImpl this$0, TicketLockDTO $info) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$info, (String)"$info");
        Supplier<LockSeatResultVO> cfr_ignored_0 = () -> TicketServiceImpl.queryUnlockedSeats$lambda$2$lambda$1(this$0, $info);
    }
}
