<template>
  <a-modal v-model="show" title="试卷详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="examPaperInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">试卷信息</span></a-col>
        <a-col :span="8"><b>试卷名称：</b>
          {{ examData.exampaperName ? examData.exampaperName : '- -' }}
        </a-col>
        <a-col :span="8"><b>试卷编号：</b>
          {{ examData.code ? examData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>状态：</b>
          <span v-if="examData.status == 0" style="color: red">下架</span>
          <span v-if="examData.status == 1" style="color: green">正常</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>创建时间：</b>
          {{ examData.createDate ? examData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>备注：</b>
          {{ examData.content ? examData.content : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">试卷选项</span></a-col>
        <a-table :columns="columns" :data-source="optionList">
          <template slot="titleShow" slot-scope="text, record">
            <template>
              <a-tooltip>
                <template slot="title">
                  {{ record.title }}
                </template>
                {{ record.title.slice(0, 10) }} ...
              </a-tooltip>
            </template>
          </template>
          <template slot="optionShow" slot-scope="text, record">
            <template>
              <a-tooltip>
                <template slot="title">
                  {{ record.contentOptions }}
                </template>
                {{ record.contentOptions.slice(0, 10) }} ...
              </a-tooltip>
            </template>
          </template>
        </a-table>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">试卷结果</span></a-col>
        <a-table :columns="columns1" :data-source="resultList"></a-table>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'
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
  name: 'userView',
  props: {
    examShow: {
      type: Boolean,
      default: false
    },
    examData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.examShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '试题名称',
        dataIndex: 'title',
        scopedSlots: { customRender: 'titleShow' }
      }, {
        title: '选项',
        dataIndex: 'contentOptions',
        scopedSlots: { customRender: 'optionShow' }
      }, {
        title: '分数',
        dataIndex: 'score'
      }, {
        title: '正确答案',
        dataIndex: 'answer',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '答案解析',
        dataIndex: 'analysis',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '试题类型',
        dataIndex: 'type',
        customRender: (text, row, index) => {
          switch (text) {
            case '1':
              return <a-tag>多结果单选</a-tag>
            case '2':
              return <a-tag>单结果单选</a-tag>
            default:
              return '- -'
          }
        }
      }]
    },
    columns1 () {
      return [{
        title: '分数开始',
        dataIndex: 'scoreStart'
      }, {
        title: '分数结束',
        dataIndex: 'scoreEnd'
      }, {
        title: '检测结果',
        dataIndex: 'title'
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      durgList: [],
      logisticsList: [],
      userInfo: null,
      examPaperInfo: null,
      resultList: [],
      optionList: []
    }
  },
  watch: {
    examShow: function (value) {
      if (value) {
        this.dataInit(this.examData.id)
      }
    }
  },
  methods: {
    dataInit (id) {
      this.$get(`/cos/exam-paper-info/${id}`).then((r) => {
        this.examPaperInfo = r.data.examPaper
        this.resultList = r.data.result
        this.optionList = r.data.option
      })
    },
    local (examData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(examData.longitude, examData.latitude)
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
