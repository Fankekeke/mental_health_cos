<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="学生姓名"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.userName"/>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">新增</a-button>-->
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="audit" @click="handleUserViewOpen(record)" title="详 情" style="margin-right: 10px"></a-icon>
          <a-icon v-if="record.status == 1" type="cloud" @click="handleViewOpen(record)" title="详 情"></a-icon>
<!--          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>-->
        </template>
      </a-table>
    </div>
    <register-add
      v-if="registerAdd.visiable"
      @close="handleregisterAddClose"
      @success="handleregisterAddSuccess"
      :registerAddVisiable="registerAdd.visiable">
    </register-add>
    <register-edit
      ref="registerEdit"
      @close="handleregisterEditClose"
      @success="handleregisterEditSuccess"
      :registerEditVisiable="registerEdit.visiable">
    </register-edit>
    <register-view
      @close="handleViewClose"
      @success="handleViewSuccess"
      :registerShow="registerView.visiable"
      :registerData="registerView.data">
    </register-view>
    <user-view
      @close="handleUserViewClose"
      :userShow="userView.visiable"
      :userData="userView.data">
    </user-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import registerAdd from './RegisterAdd.vue'
import registerEdit from './RegisterEdit.vue'
import registerView from './RegisterView.vue'
import userView from './UserView.vue'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'register',
  components: {registerAdd, registerEdit, registerView, userView, RangeDate},
  data () {
    return {
      userView: {
        visiable: false,
        data: null
      },
      advanced: false,
      registerAdd: {
        visiable: false
      },
      registerEdit: {
        visiable: false
      },
      registerView: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '学生姓名',
        dataIndex: 'studentName'
      }, {
        title: '学生头像',
        dataIndex: 'studentImages',
        customRender: (text, record, index) => {
          if (!record.studentImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.studentImages.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.studentImages.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '联系方式',
        dataIndex: 'phone',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '教师姓名',
        dataIndex: 'teacherName',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '教师头像',
        dataIndex: 'teacherImages',
        customRender: (text, record, index) => {
          if (!record.teacherImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.teacherImages.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.teacherImages.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case '1':
              return <a-tag>进行中</a-tag>
            case '2':
              return <a-tag>已完成</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '预约时间',
        dataIndex: 'registerDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '辅导预约时间',
        dataIndex: 'startDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return row.startDate + '~' + row.endDate
          } else {
            return '- -'
          }
        },
        ellipsis: true
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleUserViewOpen (row) {
      this.userView.data = row
      this.userView.visiable = true
    },
    handleUserViewClose () {
      this.userView.visiable = false
    },
    handleViewOpen (row) {
      this.registerView.data = row
      this.registerView.visiable = true
    },
    handleViewClose () {
      this.registerView.visiable = false
    },
    handleViewSuccess () {
      this.registerView.visiable = false
      this.$message.success('提交成功')
      this.fetch()
    },
    editStatus (row, status) {
      this.$post('/cos/register-info/account/status', { registerId: row.id, status }).then((r) => {
        this.$message.success('修改成功')
        this.fetch()
      })
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.registerAdd.visiable = true
    },
    handleregisterAddClose () {
      this.registerAdd.visiable = false
    },
    handleregisterAddSuccess () {
      this.registerAdd.visiable = false
      this.$message.success('新增辅导预约成功')
      this.search()
    },
    edit (record) {
      this.$refs.registerEdit.setFormValues(record)
      this.registerEdit.visiable = true
    },
    handleregisterEditClose () {
      this.registerEdit.visiable = false
    },
    handleregisterEditSuccess () {
      this.registerEdit.visiable = false
      this.$message.success('修改辅导预约成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/register-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.status === undefined) {
        delete params.status
      }
      params.userId = this.currentUser.userId
      this.$get('/cos/register-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
