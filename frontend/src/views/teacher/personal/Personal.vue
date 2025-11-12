<template>
  <a-row :gutter="20">
    <a-col :span="16">
      <a-card :loading="loading" :bordered="false" class="main-card">
        <a-form :form="form" layout="vertical" class="teacher-form">
          <a-row :gutter="20">
            <a-col :span="8">
              <a-form-item label='教师编号' v-bind="formItemLayout">
                <a-input disabled v-decorator="[
                'code',
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='注册时间' v-bind="formItemLayout">
                <a-input disabled v-decorator="[
                'createDate',
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='教师姓名' v-bind="formItemLayout">
                <a-input v-decorator="[
                'name',
                { rules: [{ required: true, message: '请输入用教师姓名!' }] }
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='出生日期' v-bind="formItemLayout">
                <a-date-picker style="width: 100%;" v-decorator="[
                'birthday',
                { rules: [{ required: true, message: '请输入出生日期!' }] }
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='联系方式' v-bind="formItemLayout">
                <a-input v-decorator="[
                'phone'
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label='性别' v-bind="formItemLayout">
                <a-select v-decorator="[
                  'sex',
                  ]">
                  <a-select-option value="1">男</a-select-option>
                  <a-select-option value="2">女</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="辅导日期" v-bind="formItemLayout">
                <a-checkbox-group v-decorator="['weeks']">
                  <a-checkbox v-for="week in weekOptions" :key="week.value" :value="week.value">
                    {{ week.label }}
                  </a-checkbox>
                </a-checkbox-group>
              </a-form-item>
            </a-col>

            <!-- 在辅导日期后添加工作时间段 -->
            <a-col :span="24">
              <a-form-item label="工作时间段" v-bind="formItemLayout" class="work-time-section">
                <div v-for="(time, index) in workTimeList" :key="index" class="time-picker-row" style="margin-bottom: 10px">
                  <a-row :gutter="12" align="middle">
                    <a-col :span="10">
                      <a-time-picker
                        format="HH:mm"
                        placeholder="开始时间"
                        :value="time.start"
                        @change="onStartTimeChange(index, $event)"            style="width: 100%"
                        class="time-picker"
                      />
                    </a-col>
                    <a-col :span="10">
                      <a-time-picker
                        format="HH:mm"
                        placeholder="结束时间"
                        :value="time.end"
                        @change="onEndTimeChange(index, $event)"            style="width: 100%"
                        class="time-picker"
                      />
                    </a-col>
                    <a-col :span="4">
                      <div class="time-action-buttons">
                        <a-button
                          v-if="workTimeList.length > 1"
                          type="danger"
                          shape="circle"
                          icon="minus"
                          @click="removeWorkTime(index)"
                          class="action-button remove-button"
                        />
                        <a-button
                          v-if="index === workTimeList.length - 1"
                          type="primary"
                          shape="circle"
                          icon="plus"
                          @click="addWorkTime"
                          class="action-button add-button"
                        />
                      </div>
                    </a-col>
                  </a-row>
                </div>
                <div v-if="workTimeList.length === 0" class="empty-time-section">
                  <a-button type="dashed" icon="plus" @click="addWorkTime">添加时间段</a-button>
                </div>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label='备注' v-bind="formItemLayout">
                <a-textarea :rows="6" v-decorator="[
                'content',
                 { rules: [{ required: true, message: '请输入备注!' }] }
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label='头像' v-bind="formItemLayout">
                <a-upload
                  name="avatar"
                  action="http://127.0.0.1:9527/file/fileUpload/"
                  list-type="picture-card"
                  :file-list="fileList"
                  @preview="handlePreview"
                  @change="picHandleChange"
                >
                  <div v-if="fileList.length < 1">
                    <a-icon type="plus"/>
                    <div class="ant-upload-text">
                      Upload
                    </div>
                  </div>
                </a-upload>
                <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                  <img alt="example" style="width: 100%" :src="previewImage"/>
                </a-modal>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
          修改
        </a-button>
      </a-card>
    </a-col>
    <a-col :span="8" v-if="expertInfo != null" style="margin-top: 30px;padding-left: 50px">
      <a-card :bordered="false" class="info-card">
        <a-row>
          <a-col :span="5">
            <a-avatar :src="'http://127.0.0.1:9527/imagesWeb/' + expertInfo.images" shape="square"
                      style="width: 100px;height: 100px;"/>
          </a-col>
          <a-col :span="24">
            <div style="font-size: 20px;font-family: SimHei">{{ expertInfo.name }}</div>
            <p style="font-size: 13px;font-family: SimHei">{{ expertInfo.code }}</p>
          </a-col>
          <a-col :span="24">
            <!-- 显示辅导日期 -->
            <div v-if="expertInfo && expertInfo.weeks" style="margin-top: 10px;">
              <div style="font-weight: bold;">辅导日期：</div>
              <div>{{ formatWeeks(expertInfo.weeks) }}</div>
            </div>
            <!-- 显示工作时间段 -->
            <div v-if="expertInfo && expertInfo.workTimes" class="info-section">
              <div class="section-title">工作时间：</div>
              <div
                v-for="(timeRange, index) in parseWorkTimes(expertInfo.workTimes)"
                :key="index"
                style="margin-top: 15px"
                class="time-display-item"
              >
                <a-tag color="blue">{{ timeRange.start }} - {{ timeRange.end }}</a-tag>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import {mapState} from 'vuex'
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

const formItemLayout = {
  labelCol: {span: 24},
  wrapperCol: {span: 24}
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      loading: false,
      courseInfo: [],
      dataLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      expertInfo: null,
      // 新增数据
      weekOptions: [
        {label: '周一', value: '1'},
        {label: '周二', value: '2'},
        {label: '周三', value: '3'},
        {label: '周四', value: '4'},
        {label: '周五', value: '5'},
        {label: '周六', value: '6'},
        {label: '周日', value: '7'}
      ],
      workTimeList: [
        {start: null, end: null}
      ]
    }
  },
  mounted () {
    this.getExpertInfo(this.currentUser.userId)
  },
  methods: {
    parseWorkTimes (workTimes) {
      if (!workTimes) return []

      return workTimes.split(',').map(timeRange => {
        const [start, end] = timeRange.split('-')
        return { start, end }
      })
    },
    // 格式化显示周几
    formatWeeks (weeks) {
      const weekMap = {
        '1': '周一',
        '2': '周二',
        '3': '周三',
        '4': '周四',
        '5': '周五',
        '6': '周六',
        '7': '周日'
      }

      if (Array.isArray(weeks)) {
        return weeks.map(w => weekMap[w]).join('、')
      } else if (typeof weeks === 'string') {
        return weeks.split(',').map(w => weekMap[w]).join('、')
      }
      return ''
    },

    // 时间段操作方法
    addWorkTime () {
      this.workTimeList.push({start: null, end: null})
    },

    removeWorkTime (index) {
      this.workTimeList.splice(index, 1)
    },

    onStartTimeChange (index, time) {
      this.$set(this.workTimeList[index], 'start', time)
    },

    onEndTimeChange (index, time) {
      this.$set(this.workTimeList[index], 'end', time)
    },
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
    },
    getListData (value) {
      let listData = []
      this.courseInfo.forEach(item => {
        if ((moment(value).format('YYYY-MM-DD')) === (moment(item.payDate).format('YYYY-MM-DD'))) {
          listData.push({type: 'success', content: '✔'})
        }
      })
      return listData || []
    },
    getExpertInfo (userId) {
      this.dataLoading = true
      this.$get(`/cos/teacher-info/detail/${userId}`).then((r) => {
        this.expertInfo = r.data.data
        this.setFormValues(this.expertInfo)
        // this.courseInfo = r.data.order
        this.dataLoading = false
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
    picHandleChange ({fileList}) {
      this.fileList = fileList
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
    setFormValues ({...expert}) {
      this.rowId = expert.id
      let fields = ['code', 'name', 'phone', 'sex', 'email', 'images', 'createDate', 'birthday', 'content']
      let obj = {}
      if (expert.weeks) {
        this.form.getFieldDecorator('weeks')
        let weeksValue = Array.isArray(expert.weeks) ? expert.weeks : expert.weeks.split(',')
        obj['weeks'] = weeksValue
      }

      // 处理工作时间段
      if (expert.workTimes) {
        if (typeof expert.workTimes === 'string' && expert.workTimes.includes('-')) {
          // 如果是逗号分隔的字符串格式
          this.workTimeList = expert.workTimes.split(',').map(timeRange => {
            const [start, end] = timeRange.split('-')
            return {
              start: start ? moment(start, 'HH:mm:ss') : null,
              end: end ? moment(end, 'HH:mm:ss') : null
            }
          })
        }
      }
      Object.keys(expert).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(expert['images'])
        }
        if (key === 'birthday') {
          if (key === 'birthday' && expert[key] != null) {
            expert[key] = moment(expert[key])
          }
        }
        if (key === 'sex') {
          expert[key] = expert[key].toString()
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = expert[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        values.birthday = moment(values.birthday).format('YYYY-MM-DD')
        if (this.workTimeList && this.workTimeList.length > 0) {
          values.workTimes = this.workTimeList
            .filter(time => time.start && time.end) // 过滤掉空的时间段
            .map(time => {
              const startTime = moment(time.start).format('HH:mm:ss')
              const endTime = moment(time.end).format('HH:mm:ss')
              return `${startTime}-${endTime}`
            })
            .join(',')
        } else {
          values.workTimes = ''
        }

        // 处理辅导日期
        if (values.weeks) {
          values.weeks = Array.isArray(values.weeks) ? values.weeks.join(',') : values.weeks
        }
        if (!err) {
          this.loading = true
          this.$put('/cos/teacher-info', {
            ...values
          }).then((r) => {
            this.$message.success('更新成功')
            this.loading = false
            this.getExpertInfo(this.currentUser.userId)
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

.main-card {
border-radius: 8px;
box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.info-card {
border-radius: 8px;
box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
background: #f8f9fa;
}

.teacher-form {
padding: 20px 0;
}

.work-time-section {
margin-top: 10px;
}

.time-picker-row {
margin-bottom: 15px;
padding: 12px;
background: #fafafa;
border-radius: 6px;
transition: all 0.3s;
}

.time-picker-row:hover {
background: #f0f0f0;
box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
}

.time-picker {
border-radius: 4px;
}

.time-action-buttons {
display: flex;
align-items: center;
justify-content: flex-start;
gap: 8px;
}

.action-button {
display: flex;
align-items: center;
justify-content: center;
}

.remove-button {
background: #ff4d4f;
border-color: #ff4d4f;
}

.add-button {
background: #1890ff;
border-color: #1890ff;
}

.empty-time-section {
text-align: center;
padding: 20px;
background: #fafafa;
border-radius: 6px;
border: 1px dashed #d9d9d9;
}

.avatar-preview {
display: flex;
align-items: center;
justify-content: center;
}

.submit-button {
margin-top: 20px;
width: 100%;
height: 40px;
font-size: 16px;
}
</style>
