<template>
  <a-drawer
    title="答题"
    placement="right"
    width="100%"
    :closable="false"
    :visible="orderShow"
    destroyOnClose
    wrapClassName="aa"
    :getContainer="false"
  >
    <div style="width: 100%;">
      <a-row>
        <a-col :span="8">
          <a-row style="margin-top: 50px">
            <a-col :span="12">
              <i style="font-size: 20px;font-family: SimHei;margin-left: 30px">我的信息</i>
              <a-card :bordered="false" v-if="expertInfo != null" style="margin-left: 25px;margin-top: 20px">
                <a-row>
                  <a-col :span="12">
                    <a-avatar :src="'http://127.0.0.1:9527/imagesWeb/' + expertInfo.images" shape="square" style="width: 100px;height: 100px;"/>
                  </a-col>
                  <a-col :span="12">
                    <div style="font-size: 20px;font-family: SimHei">{{ expertInfo.name }}</div>
                    <p style="font-size: 13px;font-family: SimHei">{{ expertInfo.code }}</p>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </a-col>
        <a-col :span="16">
          <a-card :bordered="false" :title="(index + 1) + '、' + item.title" v-for="(item, index) in optionList" :key="index" style="margin-bottom: 25px">
            <a-radio-group v-model="item.pickCheck" button-style="solid">
              <a-radio v-for="(item2, index2) in item.contentOptions.split(',')" :key="index2" :value="item2" style="margin-right: 15px;margin-top: 15px">
                {{ item2 }}
              </a-radio>
            </a-radio-group>
          </a-card>
          <div style="margin-bottom: 100px;margin-top: 50px">
            <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
              <a-button style="margin-right: .8rem">取消</a-button>
            </a-popconfirm>
            <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
          </div>
        </a-col>
      </a-row>
    </div>
<!--    <div class="drawer-bootom-button">-->
<!--      -->
<!--    </div>-->
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Map',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      loading: false,
      examPaperInfo: null,
      expertInfo: null,
      resultList: [],
      optionList: []
    }
  },
  watch: {
    'orderShow': function (value) {
      if (value) {
        this.dataInit(this.orderData.examId)
        this.getExpertInfo(this.currentUser.userId)
      }
    }
  },
  methods: {
    getExpertInfo (userId) {
      this.dataLoading = true
      this.$get(`/cos/student-info/detail/${userId}`).then((r) => {
        this.expertInfo = r.data.data
      })
    },
    onClose () {
      this.$emit('close')
    },
    dataInit (id) {
      this.$get(`/cos/exam-paper-info/${id}`).then((r) => {
        this.examPaperInfo = r.data.examPaper
        this.resultList = r.data.result
        this.optionList = r.data.option
        this.optionList.forEach(item => {
          item.pickCheck = -1
        })
      })
    },
    handleSubmit () {
      this.optionList.forEach(item => {
        if (item.pickCheck === -1) {
          this.$message.error('请选择答案')
          throw new Error('抛出异常跳出')
        }
      })
      // console.log(JSON.stringify(this.optionList))
      this.$post(`/cos/exam-paper-info/examTestCommit`, {
        userId: this.currentUser.userId,
        option: JSON.stringify(this.optionList)
      }).then((r) => {
        this.$emit('success')
      })
    }
  }
}
</script>

<style scoped>
  >>> .ant-drawer-body {
    padding: 0 !important;
  }
  >>> .ant-card-meta-title {
    font-size: 18px;
    font-family: SimHei;
  }
  >>> .ant-card-meta-description {
    font-size: 18px;
    font-family: SimHei;
  }
  >>> .ant-divider-with-text-left {
    margin: 0;
  }

  >>> .ant-card-head-title {
    font-size: 18px;
    font-family: SimHei;
  }
  >>> .ant-radio-input {
    font-size: 15px;
    font-family: SimHei;
  }
  >>> .ant-radio-button-wrapper {
    border-radius: 0;
  }
</style>
