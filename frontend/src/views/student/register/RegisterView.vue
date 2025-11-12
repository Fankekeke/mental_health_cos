<template>
  <a-modal v-model="show" title="辅导预约详情" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back1" @click="commit">
        提交
      </a-button>
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="registerData !== null"></div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import baiduMap from '@/utils/map/baiduMap'
import {mapState} from "vuex";
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'registerView',
  props: {
    registerShow: {
      type: Boolean,
      default: false
    },
    registerData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.registerShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '药品名称',
        dataIndex: 'drugName',
        scopedSlots: {customRender: 'nameShow'}
      }, {
        title: '数量',
        dataIndex: 'reserve',
        scopedSlots: {customRender: 'reserveShow'}
      }, {
        title: '所属品牌',
        dataIndex: 'brand',
        scopedSlots: {customRender: 'brandShow'}
      }, {
        title: '药品类别',
        dataIndex: 'classification',
        scopedSlots: {customRender: 'typeIdShow'}
      }, {
        title: '剂型',
        dataIndex: 'dosageForm',
        scopedSlots: {customRender: 'dosageFormShow'}
      }, {
        title: '单价',
        dataIndex: 'unitPrice',
        scopedSlots: {customRender: 'priceShow'}
      }]
    }
  },
  data () {
    return {
      remark: '',
      reference: '',
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
    }
  },
  watch: {
    registerShow: function (value) {
      if (value) {
      }
    }
  },
  mounted () {
  },
  methods: {
    commit () {
      if (!this.reference) {
        this.$message.error('请填写用药参考')
        return false
      }
      if (!this.remark) {
        this.$message.error('请填写备注')
        return false
      }
      if (this.dataList.length === 0) {
        this.$message.error('请填写用药')
        return false
      }
      let orderItem = []
      this.dataList.forEach(e => {
        orderItem.push({
          drugId: e.drugId,
          quantity: e.reserve,
          unitPrice: e.unitPrice
        })
      })
      let data = {
        userId: this.registerData.userId,
        staffId: this.currentUser.userId,
        registerId: this.registerData.id,
        orderItem: JSON.stringify(orderItem)
      }
      this.$post('/cos/register-info/register/order', data).then((r) => {
        this.$emit('success')
      })
    },
    handleChange (value, record) {
      if (value) {
        this.drugList.forEach(e => {
          if (e.id === value) {
            record.reserveAll = e.reserve
            record.brand = e.brand
            record.classification = e.classification
            record.dosageForm = e.dosageForm
            record.unitPrice = e.unitPrice
            record.drugId = e.id
            console.log(record)
          }
        })
      }
    },
    local (register) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(register.longitude, register.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
