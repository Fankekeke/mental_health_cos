<template>
  <a-card style="width: 100%" class="daily-article" :loading="loading">
    <template class="ant-card-actions" slot="actions">
      <a-icon type="step-forward" @click="newsNext" class="article-button"/>
    </template>
    <a-card-meta
      :title="title"
      :description="createBy"/>
    <span v-html="newsContent" class="article-content" style="padding: 23px;font-family: SimHei;letter-spacing: 1px;"></span>
  </a-card>
</template>
<script>
export default {
  data () {
    return {
      loading: true,
      article: {
        title: '',
        content: '',
        date: {},
        author: '',
        wc: ''
      },
      newsPage: 0,
      today: '',
      newsContent: '',
      title: '',
      createBy: '',
      newsList: []
    }
  },
  methods: {
    newsNext () {
      if (this.newsPage + 1 === this.newsList.length) {
        this.newsPage = 0
      } else {
        this.newsPage += 1
      }
      this.newsContent = `《${this.newsList[this.newsPage].title}》 ${this.newsList[this.newsPage].content}`
      this.createBy = `${this.newsList[this.newsPage].createBy}`
      this.title = `${this.newsList[this.newsPage].title}`
    },
    getPreArticle () {
      this.getArticle(this.article.date.prev)
    },
    getNextArticle () {
      if (this.article.date.next > this.today) {
        this.$message.warning('明天的文章小编还没准备好哦')
        return
      }
      this.getArticle(this.article.date.next)
    },
    getArticle (date = '') {
      this.$get('/cos/article-info/list').then((r) => {
        this.loading = false
        this.newsList = r.data.data
        if (this.newsList.length !== 0) {
          this.title = this.newsList[0].title
          this.createBy = this.newsList[0].createBy
          this.newsContent = `《${this.newsList[0].title}》 ${this.newsList[0].content}`
        }
      })
    }
  },
  mounted () {
    this.getArticle()
  }
}
</script>
<style lang="less">
  .daily-article {
    .article-button {
      font-size: 1.2rem !important;
    }
    .ant-card-body {
      padding: 18px !important;
    }
    .ant-card-head {
      padding: 0 1rem;
    }
    .ant-card-meta {
      margin-bottom: 1rem;
    }
    .article-content {
      p {
        word-wrap: break-word;
        word-break: break-all;
        text-overflow: initial;
        white-space: normal;
        font-size: .9rem !important;
        margin-bottom: .8rem;
      }
    }
  }
</style>
