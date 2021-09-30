<template>
  <div style="text-align:center">

    <div style="margin: 10px 0;padding: 0 100px 0 100px;">
      <el-input
        type="textarea"
        placeholder="请输入留言内容"
        v-model="comment"
        maxlength="200"
        show-word-limit
      ></el-input>
    </div>

    <div style="margin: 20px 0;">
      <el-button type="primary" size="medium" icon="el-icon-s-promotion" @click="submit">提交</el-button>
    </div>
    <el-divider></el-divider>
    <div style="margin: 20px 0;">
      <el-tree
      node-key="id"
      lazy
      ref="tree"
      :load="asyncLoad"
      :expand-on-click-node="true"
      :render-content="renderContent"
      >
      </el-tree>
    </div>
    <div>
      <el-button type="primary" size="medium" icon="el-icon-arrow-down" @click="nextPage"></el-button>
    </div>

  </div>
</template>

<script>
import Tree from 'vue-tree-halower'
import storage from "../assets/js/storage";

export default {
  components: { Tree},
  name: 'Home',
  data() {
    return {
      comment: "",
      pageIndex:1,
      totalPage:1,
      treeResolver: ()=>{}
    }
  },
  created() {
  },
  methods: {
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">

            <span>L{data.level} </span>
            <span>{data.label} </span>
            <span>用户：</span>
            <span>   {data.username} 于</span>
            <span>{data.createTime} 发表 </span>
            <span> 含{data.childrenCount}条</span>
            <span>
              <el-button type="text" onClick={() => {this.openReplay(data)}}>评论</el-button>
            </span>
          </span>);
    },
    nextPage(){

      this.pageIndex++
      if (this.pageIndex > this.totalPage){
        return
      }
        console.log(this.treeResolver)

        this.$axios.get("/comment?parentId=0&deep=1&size=10&currentPage="+this.pageIndex)
          .then(res => {
            if (!res.data.success) {
              console.error("error==>:" + res.data.message)
              return
            }
            this.totalPage = res.data.totalPage
            let tmp = []
            for (let item of res.data.data) {
              tmp.push({
                id: item.id,
                level:item.level,
                username: item.username,
                label: item.content,
                parentId: item.parentId,
                childrenCount: item.childrenCount,
                createTime:item.createTime
              })
            }
            this.treeResolver(tmp)
            console.log("nextPage",tmp)
          })
          .catch(res => {
            console.log(res.data.message)
          });
    },
    asyncLoad(node,resolve) {

      this.treeResolver = resolve
      console.log("asyncLoad==",node)
      //根节点的node.data 为undefine
      let pid = 0;
      if(node.data){
        pid = node.data.id
      }
      this.$axios.get("/comment?parentId=" + pid+ "&deep=1&size=10&currentPage=1")
        .then(res => {
          if (!res.data.success) {
            console.error("error==>:" + res.data.message)
            return
          }
          this.totalPage = res.data.totalPage
          let tmp = []
          for (let item of res.data.data) {
            tmp.push({
              id: item.id,
              level:item.level,
              username: item.username,
              label: item.content,
              parentId: item.parentId,
              childrenCount: item.childrenCount,
              createTime:item.createTime
            })
          }
          resolve(tmp)
          console.log("tmp",tmp)
        })
        .catch(res => {
          console.log(res.data.message)
        });
    },
    submit(){
      this.replay(0,this.comment)
      // location.reload()
    },
    replay(pid,content){
      let fd = new FormData();
      fd.append("parentId", pid);
      fd.append("content", content);

      let config = {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authentication': storage.getItem("token")
        }
      }

      this.$axios.post("/comment", fd,config).then( res => {

        if (!res.data.success ) {
          console.error("error==>:" + res.data.message)
          let msg = res.data.message

          if ( 401 === res.data.status){
            this.$message.error(msg+" \n请登录");
            this.$router.push({ path: "/login" });
            return;
          }
          this.$message.error(msg);
          return
        }
        //清空输入框
        this.comment=""

        console.log("after submit rsp:",res)
        let newNode = res.data.data
        let pn = this.$refs.tree.getNode(newNode.parentId)
        this.$refs.tree.append({
          id: newNode.id,
          username: newNode.username,
          label: newNode.content,
          level: newNode.level,
          parentId: newNode.parentId,
          childrenCount: newNode.childrenCount,
          createTime: newNode.createTime
        },pn)
        this.$message({
          message: '发布成功',
          type: 'success'
        });

      }).catch( res => {
        console.error(res)
        this.$message.error('发布失败');
      })

    },
    openReplay(data) {
      this.$prompt('', '评论', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.{3,200}/,
        inputErrorMessage: '请确保内容在3~200字之间'
      }).then(({ value }) => {
        this.replay(data.id, value);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body {
  background: #ebebeb;
  font-family: "Helvetica Neue", "Hiragino Sans GB", "Microsoft YaHei", "\9ED1\4F53", Arial, sans-serif;
  color: #222;
  font-size: 12px;
}

* {
  padding: 0px;
  margin: 0px;
}

a {
  text-decoration: none;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
