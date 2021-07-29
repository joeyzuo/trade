<template>
    <div>
        <van-tabs>
            <van-tab title="合约">

                <div v-for="(it ,index) in  brokerInfo.contracts" v-bind:key="index" @click="showPopup(it)">
                    <div class="sysbomlList">
                    <span>
                       {{it.symbol}}
                    </span>
                    </div>
                </div>
            </van-tab>
            <van-tab title="币币">
                <div v-for="(it ,index) in brokerInfo.symbols" v-bind:key="index" @click="showPopup(it)">
                    <div class="sysbomlList">
                    <span>
                        {{it.baseAsset+"/"+it.quoteAsset}}
                    </span>
                    </div>
                </div>
            </van-tab>
        </van-tabs>
        <van-popup v-model="show"  position="bottom" :style="{ height: '50%' }">
            <div >
                <van-row>
                    <van-button plain hairline type="primary" size="large" @click="toStrategy('wg')">网格策略</van-button>
                </van-row>
                <van-row>
                    <van-button plain hairline type="primary" size="large">均仓策略</van-button>
                </van-row>
            </div>

        </van-popup>
    </div>

</template>
<style lang="scss">
    .sysbomlList {
        border-bottom: 1px solid #2c3e50;

        &:hover {
            background-color: #91d5ff;
            cursor: pointer;
        }
    }
    .van-row{
        margin: 5px;
    }

</style>
<script>

    export default {
        components: {},
        name: 'Home',
        props: {
            msg: String
        },
        data() {
            return {
                collapseValue: "1",
                brokerInfo: {},
                show: false,
                symbol:undefined
                // definptions
            }
        }
        ,
        methods: {
            init() {
                this.$axios.get("/hbtc/BrokerInfo").then(rs => {
                    console.info(rs);
                    this.brokerInfo = rs.data;
                })
            }
            ,
            showPopup(item) {
                this.show = true;
                this.symbol=item;
            },
            toStrategy(name){
                this.$router.push({
                    name: 'strategyHome',
                    params: {
                        symbol: this.symbol,
                        strategyName:name
                    }
                })

            }
        }
        ,
        mounted() {
            this.init();
        }
    }
</script>