<!--
*** 感谢您查看最佳自述模板。如果你有什么建议
*** 如果这样做更好，请 Fork 仓库并创建拉取请求
*** 或者干脆用“enhancement”标签打开一个问题。
*** 再次感谢！现在去创造一些惊人的东西！：D
***
***
***
***以避免重新键入过多的信息。搜索并替换以下内容：
*** w158rk, Crypt-DAC, twitter_handle, email, project_title, project_description
-->



<!-- PROJECT SHIELDS -->
<!--
*** 我正在使用markdown“reference style”的可读性链接。
*** 引用链接用括号[]而不是括号（）括起来。
*** 有关引用变量的声明，请参见本文档的底部
*** 对于贡献者url、forks url等，这是一种可选的、简洁的语法。
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <!-- <a href="https://github.com/w158rk/Crypt-DAC">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a> -->

  <h3 align="center">Crypt-DAC</h3>

  <p align="center">
    复现Crypt-DAC
    <br />
    <a href="https://github.com/w158rk/Crypt-DAC"><strong>浏览文档 »</strong></a>
    <br />
    <br />
    <a href="https://github.com/w158rk/Crypt-DAC">查看演示</a>
    ·
    <a href="https://github.com/w158rk/Crypt-DAC/issues">报告Bug</a>
    ·
    <a href="https://github.com/w158rk/Crypt-DAC/issues">请求功能</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">目录</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">关于项目</a>
      <ul>
        <li><a href="#built-with">构建</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">入门</a>
      <ul>
        <li><a href="#prerequisites">先决条件</a></li>
        <li><a href="#installation">安装</a></li>
      </ul>
    </li>
    <li><a href="#usage">使用</a></li>
    <li><a href="#roadmap">路线图</a></li>
    <li><a href="#contributing">贡献</a></li>
    <li><a href="#license">许可证</a></li>
    <li><a href="#contact">联系人</a></li>
    <li><a href="#acknowledgements">致谢</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## 关于项目


复现Crypt-DAC，动态访问控制的仿真


### 构建

gradle build


<!-- GETTING STARTED -->
## 入门


### 先决条件

- 安装Java和gradle

### 安装

1. 克隆仓库
   ```sh
   git clone https://github.com/w158rk/Crypt-DAC.git
   ```


<!-- USAGE EXAMPLES -->
## 使用

目前支持PREDAC和PublicKeyDAC两种DAC方案，可以通过修改`app\src\main\java\org\lab510\cryptodac\App.java`进行选择切换。

```sh
gradle run --args [domino, emea, firewall1, firewall2, healthcare, university]
```

使用Python脚本可以进行多次执行，输出将存储于`output`目录，可以根据需求编辑脚本，当前执行方式

```sh
# 执行domino数据集100次
python scripts/repeat_run.py domino 100
```

得到的输出可以使用`enc_per_ur_revoke`分析每次用户角色解绑使用的公钥加密数量

```sh
python scripts/enc_per_ur_revoke.py domino 100
```

<!-- ROADMAP -->
## 路线图

### 当前版本：v0.0.1

#### 待添加功能

- 输入参数选择DAC方案

查看 [公开问题](https://github.com/w158rk/Crypt-DAC/issues) 以获取建议功能 (和已知问题)的列表.


<!-- CONTRIBUTING -->
## 贡献

贡献使开源社区成为一个值得学习、启发和创造的地方。 **非常感谢**你所做的任何贡献。

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. Push 到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull 请求



<!-- LICENSE -->
## 许可证

根据麻省理工学院的许可证(`MIT`)  分发. 查看 `LICENSE` 获取更多信息。



<!-- CONTACT -->
## 联系人

Ruikai Wang - wrk15835 AT outlook DOT com

项目链接: [https://github.com/w158rk/Crypt-DAC](https://github.com/w158rk/Crypt-DAC)



<!-- ACKNOWLEDGEMENTS -->
## 致谢





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/w158rk/Crypt-DAC.svg?style=for-the-badge
[contributors-url]: https://github.com/w158rk/Crypt-DAC/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/w158rk/Crypt-DAC.svg?style=for-the-badge
[forks-url]: https://github.com/w158rk/Crypt-DAC/network/members
[stars-shield]: https://img.shields.io/github/stars/w158rk/Crypt-DAC.svg?style=for-the-badge
[stars-url]: https://github.com/w158rk/Crypt-DAC/stargazers
[issues-shield]: https://img.shields.io/github/issues/w158rk/Crypt-DAC.svg?style=for-the-badge
[issues-url]: https://github.com/w158rk/Crypt-DAC/issues
[license-shield]: https://img.shields.io/github/license/w158rk/Crypt-DAC.svg?style=for-the-badge
[license-url]: https://github.com/w158rk/Crypt-DAC/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/w158rk