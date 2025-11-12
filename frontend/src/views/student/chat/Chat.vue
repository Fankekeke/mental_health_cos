
<template>
  <div class="chat-container">
    <!-- 左侧联系人列表 -->
    <div class="contacts-panel">
      <div class="panel-header">
        <h3>联系人</h3>
      </div>
      <div class="contact-list">
        <div
          v-for="contact in contacts"
          :key="contact.id"
          class="contact-item"
          :class="{ active: currentContact.id === contact.id }"
          @click="selectContact(contact)"
        >
          <div class="avatar">
            <img :src="contact.avatar" :alt="contact.name" v-if="contact.avatar">
            <span v-else>{{ contact.name.charAt(0) }}</span>
          </div>
          <div class="contact-info">
            <div class="contact-name">{{ contact.name }}</div>
            <div class="last-message">{{ contact.lastMessage }}</div>
          </div>
          <div class="contact-status">
            <span class="status-indicator" :class="contact.status"></span>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧聊天区域 -->
    <div class="chat-panel">
      <div class="chat-header" v-if="currentContact.name">
        <div class="avatar">
          <img :src="currentContact.avatar" :alt="currentContact.name" v-if="currentContact.avatar">
          <span v-else>{{ currentContact.name.charAt(0) }}</span>
        </div>
        <div class="contact-details">
          <div class="contact-name">{{ currentContact.name }}</div>
          <div class="contact-status">{{ currentContact.status === 'online' ? '在线' : '离线' }}</div>
        </div>
      </div>

      <div class="chat-messages" ref="messageContainer" v-if="currentContact.name">
        <div
          v-for="message in messages"
          :key="message.id"
          class="message"
          :class="{ 'sent': message.sender === 'me', 'received': message.sender !== 'me' }"
        >
          <div class="message-content">
            {{ message.content }}
            <div class="message-time">{{ formatTime(message.timestamp) }}</div>
          </div>
        </div>
      </div>

      <div class="chat-input" v-if="currentContact.name">
        <input
          type="text"
          v-model="newMessage"
          placeholder="输入消息..."
          @keyup.enter="sendMessage"
        />
        <button @click="sendMessage">发送</button>
      </div>

      <!-- 默认提示 -->
      <div class="no-contact-selected" v-if="!currentContact.name">
        <p>请选择一个联系人开始聊天</p>
      </div>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'chat',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      newMessage: '',
      currentContact: {},
      contacts: [],
      messages: []
    }
  },
  methods: {
    queryContacts () {
      this.$get(`/cos/chat-record/contacts/user/${this.currentUser.userId}`).then((r) => {
        this.contacts = r.data.data.map(contact => ({
          id: contact.teacherId,
          studentId: contact.studentId,
          name: contact.hotelName,
          avatar: 'http://127.0.0.1:9527/imagesWeb/' + contact.hotelImage,
          lastMessage: contact.lastMessage,
          status: 'online'
        }))
      })
    },
    selectContact (contact) {
      this.currentContact = contact
      // 这里可以加载与该联系人的历史消息
      this.loadMessages(contact.id, contact.studentId)
    },
    loadMessages (teacherId, studentId) {
      this.$get(`/cos/chat-record/list`, {
        userId: studentId,
        teacherId: teacherId
      }).then((r) => {
        // 将后端返回的聊天记录转换为前端需要的格式
        this.messages = r.data.data.map(message => ({
          id: message.id,
          sender: message.senderType === '0' ? 'me' : message.teacherId.toString(),
          content: message.content,
          timestamp: new Date(message.createTime)
        }))

        // 滚动到底部
        this.$nextTick(() => {
          const container = this.$refs.messageContainer
          if (container) {
            container.scrollTop = container.scrollHeight
          }
        })
      })
      console.log('Loading messages for contact:', teacherId)
    },
    sendMsg (teacherId, studentId) {
      if (this.newMessage.trim() === '') return
      this.$post('/cos/chat-record', {
        teacherId: teacherId,
        userId: studentId,
        senderType: 0,
        content: this.newMessage
      }).then((r) => {

      })
    },
    sendMessage () {
      if (this.newMessage.trim() === '') return

      // 先将消息显示在界面上
      const message = {
        id: this.messages.length + 1,
        sender: 'me',
        content: this.newMessage,
        timestamp: new Date()
      }

      this.messages.push(message)
      this.updateLastMessage(this.currentContact.id, this.newMessage)
      console.log(this.currentContact)
      // 发送消息到服务器
      this.sendMsg(this.currentContact.id, this.currentContact.studentId)

      // 清空输入框
      this.newMessage = ''

      // 滚动到底部
      this.$nextTick(() => {
        const container = this.$refs.messageContainer
        if (container) {
          container.scrollTop = container.scrollHeight
        }
      })
    },

    updateLastMessage (contactId, message) {
      const contact = this.contacts.find(c => c.id === contactId)
      if (contact) {
        contact.lastMessage = message
      }
    },

    formatTime (timestamp) {
      const date = new Date(timestamp)
      const hours = date.getHours().toString().padStart(2, '0')
      const minutes = date.getMinutes().toString().padStart(2, '0')
      return `${hours}:${minutes}`
    }
  },
  mounted () {
    this.queryContacts()
    // 初始化时滚动到底部
    this.$nextTick(() => {
      const container = this.$refs.messageContainer
      if (container) {
        container.scrollTop = container.scrollHeight
      }
    })
  }
}
</script>

<style scoped>.chat-container {
  display: flex;
  height: calc(100vh - 120px);
  background-color: #f5f5f5;
  border-radius: 1px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.contacts-panel {
  width: 300px;
  background-color: #fff;
  border-right: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
}

.panel-header {
  padding: 20px;
  border-bottom: 1px solid #e0e0e0;
}

.panel-header h3 {
  margin: 0;
  color: #333;
}

.contact-list {
  flex: 1;
  overflow-y: auto;
}

.contact-item {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  cursor: pointer;
  border-bottom: 1px solid #f0f0f0;
  transition: background-color 0.2s;
}

.contact-item:hover {
  background-color: #f9f9f9;
}

.contact-item.active {
  background-color: #e3f2fd;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #4a90e2;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
  margin-right: 15px;
  overflow: hidden;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.contact-info {
  flex: 1;
  overflow: hidden;
}

.contact-name {
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.last-message {
  font-size: 13px;
  color: #777;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.contact-status {
  display: flex;
  align-items: center;
}

.status-indicator {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  display: inline-block;
}

.status-indicator.online {
  background-color: #4caf50;
}

.status-indicator.offline {
  background-color: #9e9e9e;
}

.chat-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #fafafa;
}

.chat-header {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
}

.chat-header .avatar {
  width: 45px;
  height: 45px;
  margin-right: 15px;
}

.contact-details .contact-name {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 3px;
}

.contact-details .contact-status {
  font-size: 13px;
  color: #777;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.message {
  max-width: 70%;
  margin-bottom: 15px;
  position: relative;
}

.message.sent {
  align-self: flex-end;
}

.message.received {
  align-self: flex-start;
}

.message-content {
  padding: 12px 15px;
  border-radius: 18px;
  word-wrap: break-word;
  position: relative;
}

.message.sent .message-content {
  background-color: #4a90e2;
  color: white;
  border-bottom-right-radius: 5px;
}

.message.received .message-content {
  background-color: #fff;
  color: #333;
  border-bottom-left-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.message-time {
  font-size: 11px;
  text-align: right;
  margin-top: 5px;
  opacity: 0.8;
}

.chat-input {
  display: flex;
  padding: 15px;
  background-color: #fff;
  border-top: 1px solid #e0e0e0;
}

.chat-input input {
  flex: 1;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 20px;
  outline: none;
  font-size: 14px;
}

.chat-input button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
}

.chat-input button:hover {
  background-color: #3a7bc8;
}

.no-contact-selected {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fafafa;
}

.no-contact-selected p {
  color: #777;
  font-size: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .contacts-panel {
    width: 80px;
  }

  .contact-info, .contact-details {
    display: none;
  }

  .contact-item {
    justify-content: center;
    padding: 15px 10px;
  }

  .contact-item .avatar {
    margin-right: 0;
  }
}
</style>
