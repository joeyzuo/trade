<template>
    <div>
        <van-cell is-link arrow-direction="left" to="/"></van-cell>
        <div style="display: flex;flex-direction: column">
            <div style="flex: 1 1 100px">
                1
            </div>
            <div style="flex: auto">
                <van-form >
                    <van-field
                            readonly
                            clickable
                            name="picker"
                            :value="data.direction"
                            label="方向"
                            placeholder="点击选择方向"
                            @click="showPickerDirection = true"
                    />
                    <van-popup v-model="showPickerDirection" position="bottom">
                        <van-picker
                                show-toolbar
                                :columns="directionColumns"
                                @confirm="directionConfirm"
                                @cancel="showPickerDirection = false"
                        />
                    </van-popup>
                    <van-row>
                        <van-col span="11">
                            <van-field
                                    v-model="data.startPrice"
                                    label-width="0px"
                                    placeholder="开始价格"
                            />
                        </van-col>
                        <van-col span="2">-</van-col>
                        <van-col span="11">
                            <van-field
                                    v-model="data.overPrice"
                                    label-width="0px"
                                    placeholder="结束价格"
                            />
                        </van-col>
                    </van-row>
                    <van-field
                            v-model="data.gridNumber"
                            name="网格"
                            label="网格"
                            placeholder="网格"

                    />
                    <van-row>
                        <van-field
                                v-model="data.gridProfit"
                                label="每格利润"
                                readonly
                        />
                    </van-row>
                    <van-field
                            v-model="data.tradeNum"
                            name="交易数量"
                            label="交易数量"
                            placeholder="交易数量"
                    />
                    <van-row>
                        <van-col span="12" >
                            <van-field
                                    v-model="data.totalFunds"
                                    label="余额"
                                    readonly
                            />
                        </van-col>
                        <van-col span="12">
                            <van-field
                                    v-model="data.needFunds"
                                    label="需要金额"
                                    readonly
                            />
                        </van-col>
                    </van-row>
                    <van-field
                            v-model="data.stopLoss"
                            label="止损价格"
                            placeholder="止损价格"
                    />
                    <van-field
                            v-model="data.fee"
                            label="手续费"
                            placeholder="%"
                    />
                    <div style="margin: 16px;">
                        <van-button round block type="info" native-type="submit" @click="onSubmit">提交</van-button>
                    </div>
                </van-form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "StrategyHome",
        props: {
            symbol: Object,
            strategyName: String
        },
        data() {
            return {
                ws: new WebSocket("wss://wsapi.bhex.co/openapi/quote/ws/v2"),
                data: {
                    startPrice: "",
                    overPrice: "",
                    gridNumber: "",
                    gridMode: "",
                    gridProfit: "",
                    tradeNum: "",
                    needFunds: "",
                    totalFunds: "",
                    unit: "",
                    stopLoss: "",
                    fee: "",
                    direction: "",
                    strategy:"",
                    symbol:"",
                },
                directionColumns: ['多', '空'],
                showPickerDirection: false
            }
        },
        methods: {
            directionConfirm(val) {
                this.data.direction = val;
                this.showPickerDirection = false;
            },
            onSubmit() {
                this.$axios.post("/hbtc/createStrategy",this.data).then(rs=>{
                    console.info(rs);
                })
            },
            init() {
                console.info(this.symbol);
                console.info(this.strategyName);
                this.data.strategy=this.straightepsilon;
                this.data.symbol=this.symbol;
                this.ws.onopen = (evt) => {
                    console.log("Connection open ...");
                    let params = {
                        "topic": "depth",
                        "event": "sub",
                        "params": {
                            "binary": false,
                            "symbol": this.symbol.symbol,
                        }
                    }
                    this.ws.send(JSON.stringify(params));
                };

                this.ws.onmessage = function (evt) {
                    console.log("Received Message: " + evt.data);
                };

                this.ws.onclose = function (evt) {
                    console.log("Connection closed.");
                };
            }
        },
        computed:{
            getNeedFunds:function () {
                return ""
            }
        },
        mounted() {
            this.init();

        }
        ,
        beforeDestroy() {
            this.ws.close();
        }
    }
</script>

<style lang="scss">

</style>