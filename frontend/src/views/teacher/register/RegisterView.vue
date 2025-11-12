<template>
  <a-modal v-model="show" title="辅导预约详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back1" @click="commit">
        提交
      </a-button>
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="registerData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="20">
        <a-col style="margin-bottom: 15px">
          <span style="font-size: 16px;font-weight: 600;color: #1890ff">
            <a-icon type="form" style="margin-right: 8px" />
            备注/辅导内容
          </span>
        </a-col>
        <a-col :span="24">
          <div class="textarea-wrapper">
            <a-textarea
              v-model="remark"
              placeholder="请输入备注信息..."
              :auto-size="{ minRows: 6, maxRows: 8 }"
              class="custom-textarea"
            />
            <div class="textarea-footer">
              <span class="char-count">{{ remark ? remark.length : 0 }}/500</span>
            </div>
          </div>
        </a-col>
        <a-col :span="24" style="margin-top: 20px">
          <div class="textarea-wrapper">
            <a-textarea
              v-model="reference"
              placeholder="请输入辅导内容..."
              :auto-size="{ minRows: 6, maxRows: 8 }"
              class="custom-textarea"
            />
            <div class="textarea-footer">
              <span class="char-count">{{ reference ? reference.length : 0 }}/1000</span>
            </div>
          </div>
        </a-col>
      </a-row>
    </div>
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
        this.$message.error('请填写辅导内容')
        return false
      }
      if (!this.remark) {
        this.$message.error('请填写备注')
        return false
      }
      let data = {
        id: this.registerData.id,
        remark: this.remark,
        reference: this.reference
      }
      this.$put('/cos/register-info', data).then((r) => {
        this.$emit('success')
      })
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

<style scoped>.textarea-wrapper {
  position: relative;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  transition: all 0.3s;
}

.textarea-wrapper:hover {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

.textarea-wrapper:focus-within {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
  outline: 0;
}

.custom-textarea {
  border: none !important;
  border-radius: 6px !important;
  padding: 12px !important;
}

.custom-textarea:focus {
  box-shadow: none !important;
}

.textarea-footer {
  display: flex;
  justify-content: flex-end;
  padding: 8px 12px;
  border-top: 1px solid #f0f0f0;
  background-color: #fafafa;
  border-radius: 0 0 6px 6px;
}

.char-count {
  font-size: 12px;
  color: #8c8c8c;
}

.title-section {
  display: flex;
  align-items: center;
  padding: 12px 0;
}
</style>
