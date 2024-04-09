<template>
  <a-modal v-model="show" title="选择答题试卷" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='选择试卷' v-bind="formItemLayout">
            <a-select @change="handleChange" v-decorator="[
            'paperId',
            { rules: [{ required: true, message: '请选择!' }] }
            ]">
              <a-select-option :value="item.id" v-for="(item, index) in examList" :key="index">{{ item.exampaperName }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <div style="font-size: 13px;font-family: SimHei" v-if="examData !== null">
        <a-row style="padding-left: 5px;padding-right: 5px;">
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
        <a-row style="padding-left: 5px;padding-right: 5px;">
          <a-col :span="8"><b>创建时间：</b>
            {{ examData.createDate ? examData.createDate : '- -' }}
          </a-col>
          <a-col :span="8"><b>备注：</b>
            {{ examData.content ? examData.content : '- -' }}
          </a-col>
        </a-row>
        <br/>
      </div>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'BulletinAdd',
  props: {
    bulletinAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.bulletinAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      examData: null,
      examList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.selectExamList()
  },
  methods: {
    handleChange (value) {
      this.examList.forEach(item => {
        if (item.id === value) {
          this.examData = item
        }
      })
    },
    selectExamList () {
      this.$get('/cos/exam-paper-info/list').then((r) => {
        this.examList = r.data.data
      })
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.$emit('success', values.paperId)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
