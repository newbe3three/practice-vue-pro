<!--TODO: markdown -->
<template>
  <div class="index-page" v-loading="isLoading">

    <div id="vditor" class="vditor" :style="vditorClass" />
  </div>

</template>

<script>
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import {getAccessToken, getToken} from '@/utils/auth'
export default {
  name: 'MarkdownEditor',
  props: ["height"],
  data() {
    return {
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/admin-api/infra/file/upload", // 请求地址
      headers: { Authorization: "Bearer " + getAccessToken() }, // 设置上传的请求头部
      isLoading: true,
      isMobile: window.innerWidth <= 960,
      vditor: null
    }
  },
  created() {

  },
  components: {

  },
  computed:{
    vditorClass: function(){
      return {
        height: this.height + 'px'
      }
    }
  },
  mounted() {
    this.initVditor()
    this.$nextTick(() => {
      this.isLoading = false
    })
  },
  methods: {
    initVditor() {
      const that = this
      const options = {
        width: this.isMobile ? '100%' : '100%',
        height: '0',
        tab: '\t',
        counter: '999999',
        typewriterMode: true,
        mode: 'wysiwyg',
        preview: {
          delay: 100,
          show: !this.isMobile
        },
        outline: true,
        upload: {
          max: 5 * 1024 * 1024,
          handler(file) {
            let formData = new FormData()
            for (let i in file) {
              formData.append('smfile', file[i])
            }
            let request = new XMLHttpRequest()
            // 图片上传路径
            request.open('POST', process.env.PICTURE_API + '/ckeditor/imgUpload?token=' + getToken())
            request.onload = that.onloadCallback
            request.send(formData)
          }
        }
      }
      this.vditor = new Vditor('vditor', options)
      // this.vditor.focus()
    },
    onloadCallback(oEvent) {
      const currentTarget = oEvent.currentTarget
      console.log("返回的结果", currentTarget)
      if (currentTarget.status !== 200) {
        return this.$message({
          type: 'error',
          message: currentTarget.status + ' ' + currentTarget.statusText
        })
      }
      let resp = JSON.parse(currentTarget.response)
      let imgMdStr = ''
      if (resp.uploaded !== 1) {
        return this.$message({
          type: 'error',
          message: resp.error.message
        })
      }
      if (resp.uploaded === 1) {
        imgMdStr = `![${resp.fileName}](${resp.url})`
      }
      this.vditor.insertValue(imgMdStr)
    },
    //获取data
    getData: function() {
      // let text = localStorage.getItem('vditorvditor')
      // 返回的文本
      // return this.$commonUtil.markdownToHtml(text);

      return this.vditor.getHTML();
    },
    setData: function(data) {

      // console.log("将html转", this.vditor.html2md(data))
      var that = this;
      this.$nextTick(() => {
        //DOM现在更新了
        that.initVditor()

        let markdownText = that.$commonUtil.htmlToMarkdown(data)
        console.log("转换前", data)
        console.log("得到的html", markdownText)
        localStorage.setItem('vditorvditor', markdownText)
      });
    },
    initData: function () {
      var that = this
      this.$nextTick(() => {
        that.vditor.setValue("")
      });
    }
  }
}
</script>

<style>
.vditor-panel {
  line-height: 0px;
}
.index-page {
  width: 100%;
  height: 100%;
  background-color: #FFFFFF;
}
.vditor {
  width: 100%;
  /*height: calc(100vh - 100px);*/
  top: 20px;
  /*margin: 20px auto;*/
  text-align: left;
}
.vditor-reset {
  font-size: 14px;
}
.vditor-textarea {
  font-size: 14px;
  height: 100% !important;
}
</style>
