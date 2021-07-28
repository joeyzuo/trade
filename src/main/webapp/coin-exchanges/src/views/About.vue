<template>
    <div>
        <van-cell is-link @click="showPopup" arrow-direction="left">展示弹出层</van-cell>
        <van-popup v-model="show" position="bottom">
            <van-tabs @click="onClick">
                <van-tab title="合约">

                    <div v-for="it of  brokerInfo.contracts" v-bind:key="it">
                        <div class="sysbomlList">
                    <span>
                       {{it.symbol}}
                    </span>
                        </div>
                    </div>
                </van-tab>
                <van-tab title="币币">
                    <div v-for="it of brokerInfo.symbols" v-bind:key="it">
                        <div class="sysbomlList">
                    <span>
                        {{it.baseAsset+"/"+it.quoteAsset}}
                    </span>
                        </div>
                    </div>
                </van-tab>
            </van-tabs>
        </van-popup>


    </div>

</template>
<style lang="scss">
.sysbomlList{
    border-bottom:1px solid #2c3e50;
    &:hover{
        background-color: #91d5ff;
        cursor: pointer;
    }
}
</style>
<script>

    export default {
        components: {},
        name: 'About',
        props: {
            msg: String
        },
        data() {
            return {
                collapseValue: "1", brokerInfo: {}, bsymbol: {}, csymbol: {},
               show:false
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
            showPopup() {
                this.show = true;
            }
        }
        ,
        mounted() {
            this.init();
        }
    }
</script>