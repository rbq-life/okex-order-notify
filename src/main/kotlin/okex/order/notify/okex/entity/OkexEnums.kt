package okex.order.notify.okex.entity

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
enum class AcctLv(val level: Int, val desc: String) {
    LEVEL_1(1, "简单交易模式"),
    LEVEL_2(2, "单币种保证金模式"),
    LEVEL_3(3, "跨币种保证金模式"),
    LEVEL_4(4, "组合保证金模式"),
    LEVEL_0(-1, "未知"),
    ;

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = AcctLv::class)
    object AcctLvSerializer : KSerializer<AcctLv> {
        val ENUM_MAP = values().associateBy { it.level }
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("AcctLv", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): AcctLv {
            return ENUM_MAP[decoder.decodeString().toInt()] ?: LEVEL_0
        }

        override fun serialize(encoder: Encoder, value: AcctLv) {
            encoder.encodeString(value.level.toString())
        }

    }
}

@Serializable
enum class MarginBalanceType(val desc: String) {
    add("增加"),
    reduce("减少"),
    ;
}

@Serializable
enum class GreeksType(val desc: String) {
    PA("币本位"),
    BS("美元本位"),
    ;
}

@Serializable
enum class IsoMode(val desc: String) {
    automatic("开仓划转"),
    autonomy("自主划转"),
    ;
}

@Serializable
enum class OrderState(val desc: String) {
    live("等待成交"),
    partially_filled("部分成交"),
    ;
}

@Serializable
enum class InstType(val desc: String) {
    MARGIN("币币杠杆"),
    SWAP("永续合约"),
    FUTURES("交割合约"),
    OPTION("期权"),
    ;
}

@Serializable
enum class MgnMode(val desc: String) {
    isolated("逐仓"),
    cross("全仓"),
    ;
}

@Serializable
enum class TdMode(val desc: String) {
    isolated("逐仓"),
    cross("全仓"),
    cash("非保证金"),
    ;
}

@Serializable
enum class PosSide(val desc: String) {
    long("双向持仓多头"),
    short("双向持仓空头"),
    net("单向持仓"),
    ;
}

//long_short_mode：双向持仓 net_mode：单向持仓
@Serializable
enum class PosMode(val desc: String) {
    long_short_mode("双向持仓"),
    net_mode("单向持仓"),
    ;
}

@Serializable
enum class TradeSide(val desc: String) {
    buy("买"),
    sell("卖"),
    ;
}

@Serializable
enum class OrdType(val desc: String) {
    @SerialName("market")
    MARKET("市价单"),

    @SerialName("limit")
    LIMIT("限价单"),

    @SerialName("post_only")
    POST_ONLY("只做maker单"),

    @SerialName("fok")
    FOK("全部成交或立即取消"),

    @SerialName("ioc")
    IOC("立即成交并取消剩余"),

    @SerialName("optimal_limit_ioc")
    OPTIMAL_LIMIT_IOC("市价委托立即成交并取消剩余（仅适用交割、永续）"),
    ;
}

@Serializable
enum class Category(val desc: String) {
    twap("TWAP自动换币"),
    adl("ADL自动减仓"),
    full_liquidation("强制平仓"),
    partial_liquidation("强制减仓"),
    delivery("交割"),
    ddh("对冲减仓类型订单"),
}

/**
 * 1：买入
 * 2：卖出
 * 3：开多
 * 4：开空
 * 5：平多
 * 6：平空
 * 9：市场借币扣息
 * 11：转入
 * 12：转出
 * 14：尊享借币扣息
 * 160：手动追加保证金
 * 161：手动减少保证金
 * 162：自动追加保证金
 * 114：自动换币买入
 * 115：自动换币卖出
 * 118：系统换币转入
 * 119：系统换币转出
 * 100：强减平多
 * 101：强减平空
 * 102：强减买入
 * 103：强减卖出
 * 104：强平平多
 * 105：强平平空
 * 106：强平买入
 * 107：强平卖出
 * 110：强平换币转入
 * 111：强平换币转出
 * 125：自动减仓平多
 * 126：自动减仓平空
 * 127：自动减仓买入
 * 128：自动减仓卖出
 * 131：对冲买入
 * 132：对冲卖出
 * 170：到期行权
 * 171：到期被行权
 * 172：到期作废
 * 112：交割平多
 * 113：交割平空
 * 117：交割/期权穿仓补偿
 * 173：资金费支出
 * 174：资金费收入
 * 200:系统转入
 * 201:手动转入
 * 202:系统转出
 * 203:手动转出
 */
@Serializable
enum class SubType(val type: String, val desc: String) {
    SUB_TYPE_1("1", "买入"),
    SUB_TYPE_2("2", "卖出"),
    SUB_TYPE_3("3", "开多"),
    SUB_TYPE_4("4", "开空"),
    SUB_TYPE_5("5", "平多"),
    SUB_TYPE_6("6", "平空"),
    SUB_TYPE_9("9", "市场借币扣息"),
    SUB_TYPE_11("11", "转入"),
    SUB_TYPE_12("12", "转出"),
    SUB_TYPE_14("14", "尊享借币扣息"),
    SUB_TYPE_160("160", "手动追加保证金"),
    SUB_TYPE_161("161", "手动减少保证金"),
    SUB_TYPE_162("162", "自动追加保证金"),
    SUB_TYPE_114("114", "自动换币买入"),
    SUB_TYPE_115("115", "自动换币卖出"),
    SUB_TYPE_118("118", "系统换币转入"),
    SUB_TYPE_119("119", "系统换币转出"),
    SUB_TYPE_100("100", "强减平多"),
    SUB_TYPE_101("101", "强减平空"),
    SUB_TYPE_102("102", "强减买入"),
    SUB_TYPE_103("103", "强减卖出"),
    SUB_TYPE_104("104", "强平平多"),
    SUB_TYPE_105("105", "强平平空"),
    SUB_TYPE_106("106", "强平买入"),
    SUB_TYPE_107("107", "强平卖出"),
    SUB_TYPE_110("110", "强平换币转入"),
    SUB_TYPE_111("111", "强平换币转出"),
    SUB_TYPE_125("125", "自动减仓平多"),
    SUB_TYPE_126("126", "自动减仓平空"),
    SUB_TYPE_127("127", "自动减仓买入"),
    SUB_TYPE_128("128", "自动减仓卖出"),
    SUB_TYPE_131("131", "对冲买入"),
    SUB_TYPE_132("132", "对冲卖出"),
    SUB_TYPE_170("170", "到期行权"),
    SUB_TYPE_171("171", "到期被行权"),
    SUB_TYPE_172("172", "到期作废"),
    SUB_TYPE_112("112", "交割平多"),
    SUB_TYPE_113("113", "交割平空"),
    SUB_TYPE_117("117", "交割/期权穿仓补偿"),
    SUB_TYPE_173("173", "资金费支出"),
    SUB_TYPE_174("174", "资金费收入"),
    SUB_TYPE_200("200", "系统转入"),
    SUB_TYPE_201("201", "手动转入"),
    SUB_TYPE_202("202", "系统转出"),
    SUB_TYPE_203("203", "手动转出"),
    ;
}