# EusAccountPro

[**`简体中文`**](README.md) **/** [**`English`**](README-EN.md)

> **EXTRA BRANCH** [**English** Ver.](https://github.com/EusMC/EusAccountPro/tree/english)

## 简介

使用 Bukkit / Spigot 服务端时，可能玩家的账户会被黑客破解 (正版玩家也不例外) ，为此，我们尝试去寻找解决方法，最终在 SpigotMC 发现了 [**`MineSecure`**](https://www.spigotmc.org/resources/minesecure.699/) ，可以使用 Google 开发的**二步验证** (Two Factor Authentication) 对玩家的账户进行保护 ，插件本身完美，但是定价高昂，所以我们尝试自制类似于 **`MineSecure`** 的插件，并且**免费开源**，作为服务器管理者使用的插件、插件编写者借鉴的资料

![YBI@F_NJA~W__F@WJB`N~X3.jpg](https://i.loli.net/2020/04/19/T3EVFY8AwDfLdek.jpg)

## 语言支持

- 简体中文 √
- English √

## 功能

- 使用 Google 二步验证保护玩家的账户
- 管理员可直接强制性控制玩家二步验证激活状态
- 可使用多种数据库 (JsonDB / ymlDB / MySQL / SQLite) 进行二步验证数据管理 **(MySQL / SQLite / ymlDB 正在开发)**
- 可使用 `Google Authenticator` 或 `Authy` 等同类 APP 绑定二步验证
- [`config.yml`](/src/main/resources/config.yml) 可自定义

## 安装

前往 [`SpigotMC`](https://www.spigotmc.org/resources/eusaccountpro.77573/) 或 [`Github Releases`](https://github.com/EusMC/EusAccountPro/releases) 下载**服务端对应**版本，将其放入`/plugins`文件夹

## 命令

### eap
- `/eap safepoint` 记录玩家安全点
- `/eap create` 创建二步验证
- `/eap delete` 删除自己的二步验证
- `/eap verify <code>` 初始化验证

### 2fa
- `/2fa <code>` 进服二步验证

### eapre
> 需要管理员权限
- `/eapre <玩家名>` 强制删除任意玩家二步验证

## 权限

- `EusAccountPro.common` EusAccountPro的普通权限 (默认情况下已授予)
- `EusAccountPro.admin` EusAccountPro的管理员权限，可以强制删除任意玩家二步验证

## config.yml

```yaml
Storage:
  type: JSON # JSON, SQLite, MySQL, ymlDB
Account:
  Display: EusMC # 包含在二维码内服务器的名称，可自定义，使用APP扫描后会显示该名称
```

## 数据库

- `JsonDB` 使用 Json 文件进行玩家数据存储 **(默认选择，文件格式主流，跨平台，在 `FastJson` 的辅助下读写快速)**
- `ymlDB` 使用 yml 文件进行玩家数据存储 **(敬请期待)**
- `MySQL` 使用 MySQL 数据库进行玩家数据存储 **(敬请期待)**
- `SQLite` 使用 SQLite 数据库进行玩家数据存储 **(敬请期待)**

## 开源说明

> 本项目**唯一**开源地址: [https://github.com/EusMC/EusAccountPro](https://github.com/EusMC/EusAccountPro)

使用 **`Apache License 2.0`** 进行开源，其中，若二次创作中仍然使用了 `FastJson` 解析器，请按照 `FastJson` [官方开源项目](https://github.com/alibaba/fastjson) 的要求进行处理

## 开发团队

[![桉树叶](https://www.eumc.cc/images/logo_text_black.png)](https://www.eumc.cc)

**设计 & 编程** `ElaBosak233`

**框架优化** `leavessoft`

**内测参与名单(排名不分先后)**

- `leavessoft`
- `tianyi_xiaoyi`
- `ElaBosak233`
