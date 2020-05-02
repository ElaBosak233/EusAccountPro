# EusAccountPro

[**`简体中文`**](README.md) **/** [**`English`**](README-EN.md)

## Introduction

When using the Bukkit / Spigot server, the player's account may be hacked (genuine players are no exception). For this reason, we tried to find a solution, and finally found [**`MineSecure`**](https://www.spigotmc.org/resources/minesecure.699/) in SpigotMC. You can use the **Two Factor Authentication** developed by Google so that player's account will be protected, the plug-in itself is perfect, but the price is high, so we try to make a plug-in similar to **`MineSecure`**, and it is free and open source, as a plug-in used by server managers, and materials studied by plug-in authors

## Features

- Two Factor Authentication developed by Google
- The administrator can directly control the player's Two Factor Authentication status
- Multiple databases (JsonDB / ymlDB / MySQL / SQLite) can be used for two-step verification data management **(MySQL / SQLite / ymlDB is under development)**
- You can use the similar apps such as `Google Authenticator` or` Authy` to bind two-step verification
- [`config.yml`](/src/main/resources/config.yml) can be customized

## Installation

Go to `SpigotMC` or [`Github Releases`](https://github.com/EusMC/EusAccountPro/releases) to download the **server corresponding** version, and put it in the `/plugins` folder

## Commands

### eap
- `/eap safepoint` Record player safety points
- `/eap create` Create 2FA
- `/eap delete` Delete your 2FA
- `/eap verify <code>` Initial verification

### 2fa
- `/2fa <code>` Verify when you're entering the server

### eapre
> OP Only
- `/eapre <PlayerName>` Forcibly delete any player's 2FA

## Permissions

- `EusAccountPro.common` EusAccountPro general permissions (granted by default)
- `EusAccountPro.admin` EusAccountPro administrator rights

## Database

-`JsonDB` uses Json files for player data storage **(selected by default, file format mainstream, cross-platform, read and write quickly with the help of `FastJson`)**
-`ymlDB` uses yml files for player data storage **(coming soon)**
-`MySQL` uses MySQL database for player data storage **(coming soon)**
-`SQLite` uses SQLite database for player data storage **(coming soon)**

## Open-Source

> The **only** open source address of this project: [https://github.com/EusMC/EusAccountPro](https://github.com/EusMC/EusAccountPro)

Use **`Apache License 2.0`** for open source, if you still use `FastJson` parser in the second creation, please follow `FastJson`'s [Official Open Source Project](https://github.com/alibaba/fastjson) request processing

## Development Team

[![桉树叶](https://www.eumc.cc/images/logo_text_black.png)](https://www.eumc.cc)

**Design & Programming** `ElaBosak233`

**Framework Optimization** `leavessoft`

**List of internal test participants (names not listed in order)**

- `leavessoft`
- `tianyi_xiaoyi`
- `ElaBosak233`
