<template>
  <a-card :bordered="false" class="card-area">
    <a-list
      item-layout="vertical"
      size="large"
      :data-source="schedules"
      :grid="{ gutter: 24, column: 2, xs: 1, sm: 1, md: 2, lg: 2, xl: 2, xxl: 2 }"
    >
      <a-list-item slot="renderItem" slot-scope="item">
        <a-card class="schedule-card" hoverable style="padding: 25px">
          <a-card-meta :title="item.name" :description="item.teacherName">
            <a-avatar
              slot="avatar"
              :src="'http://127.0.0.1:9527/imagesWeb/' + item.teacherImages"
              size="large"
              class="teacher-avatar"
            />
          </a-card-meta>
          <div class="schedule-info">
            <a-row :gutter="16">
              <a-col :span="12">
                <p class="info-item">
                  <a-icon type="calendar" class="info-icon" />
                  日期: {{ item.taskDate }}
                </p>
              </a-col>
              <a-col :span="12">
                <p class="info-item">
                  <a-icon type="clock-circle" class="info-icon" />
                  时间: {{ item.startDate }} - {{ item.endDate }}
                </p>
              </a-col>
            </a-row>
            <p class="status-item">
              状态:
              <a-tag v-if="item.status === '1'" color="green">可预约</a-tag>
              <a-tag v-else color="red">已满</a-tag>
            </p>
          </div>
          <div class="card-actions" v-if="item.status === '1'">
            <a-button type="primary" @click="reserveSchedule(item)" class="action-btn">
              <a-icon type="check-circle" />
              预约
            </a-button>
            <a-button type="default" class="action-btn" @click="goToChat(item)">
              <a-icon type="message" />
              在线沟通
            </a-button>
          </div>
        </a-card>
      </a-list-item>
    </a-list>
  </a-card>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'schedule',
  data () {
    return {
      schedules: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
    this.queryScheduleReserve()
  },
  methods: {
    queryScheduleReserve () {
      this.$get('/cos/schedule-info/queryScheduleReserve').then((r) => {
        this.schedules = r.data.data // 假设数据在 r.data.data 中
      })
    },
    reserveSchedule (schedule) {
      // 实现预约逻辑
      this.$post(`/cos/register-info`, {
        userId: this.currentUser.userId,
        staffId: schedule.staffIds,
        registerDate: schedule.taskDate,
        startDate: schedule.startDate,
        endDate: schedule.endDate,
        status: 1,
        scheduleId: schedule.id
      }).then((r) => {
        this.$message.success('预约成功')
        this.queryScheduleReserve() // 重新加载数据
      }).catch(() => {
        this.$message.error('预约失败')
      })
    },
    goToChat (schedule) {
      this.$post('/cos/chat-record/defaultChat', {
        teacherId: schedule.staffIds,
        userId: this.currentUser.userId,
        senderType: 0,
        content: '你好'
      }).then((r) => {
        // 跳转到聊天页面，并传递默认消息
        this.$router.push({
          path: '/student/chat'
        })
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
//@import "../../../../static/less/Common";

.card-area {
  background-color: #fff;
}

.schedule-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
}

.teacher-avatar {
  background-color: #fff;
  border: 2px solid #e8e8e8;
}

.schedule-info {
  margin-top: 16px;

  .info-item {
    margin-bottom: 8px;
    color: #595959;
    font-size: 14px;

    .info-icon {
      color: #1890ff;
      margin-right: 8px;
    }
  }

  .status-item {
    margin-top: 12px;
    font-weight: 500;
  }
}

.card-actions {
  display: flex;
  gap: 12px;
  margin-top: 16px;

  .action-btn {
    flex: 1;
    border-radius: 4px;
  }
}

@media (max-width: 768px) {
  .card-actions {
    flex-direction: column;
  }
}
</style>
