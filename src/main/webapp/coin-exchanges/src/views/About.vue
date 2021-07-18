<template>
    <Collapse v-model="collapseValue">
        <Panel name="1">
            合约策略
            <div slot="content">
                <Row>
                    <Select v-model="csymbol" style="width:200px">
                        <Option v-for="item in brokerInfo.contracts" :value="item" :key="item.symbol">{{ item.symbol }}
                        </Option>
                    </Select>
                </Row>
                <Row>
                    <Col span="12">col-12</Col>
                    <Col span="12">col-12</Col>
                </Row>
            </div>
        </Panel>
        <Panel name="2">
            币币策略
            <div slot="content">
                <Select v-model="bsymbol" style="width:200px">
                    <Option v-for="item in brokerInfo.symbols" :value="item" :key="item.symbol">{{ item.symbol}}
                    </Option>
                </Select>
            </div>
        </Panel>
    </Collapse>

</template>
<script>


    export default {
        components: { },
        name: 'About',
        props: {
            msg: String
        },
        data() {
            return {
                collapseValue: "1", brokerInfo: {}, bsymbol: {}, csymbol: {},
                value: "",
                // define options
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
            cs() {

            }
        }
        ,
        mounted() {
            //this.init();
        }
    }
</script>