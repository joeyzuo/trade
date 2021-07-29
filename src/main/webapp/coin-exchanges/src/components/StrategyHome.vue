<template>
    <div>
        <van-cell is-link arrow-direction="left" to="/"></van-cell>
        <div style="display: flex">
            <div style="flex: 1 1 100px">
                1
            </div>
            <div style="flex: auto">
                2
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
            return {}
        },
        methods: {
            init() {
                console.info(this.symbol);
                console.info(this.strategyName);
            }
        },
        mounted() {
            this.init();
        }
    }
</script>

<style lang="scss">

</style>