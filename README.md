# MiniSurvivalKit
本插件为生存服务器添加了若干小功能，旨在提升管理效率并优化玩家的游玩体验。
目前只添加了自动给予药水效果的功能，未来会添加更多功能。

支持的版本：实测可支持 Paper 1.21.1（理论上支持 Spigot/Paper 1.19-1.21.4，请测试后使用）。

# Introduction
/surkit 为插件的主命令。

目前仅有药水效果功能，有以下子命令：

| 命令 | 说明 |
|------|------|
| `/surkit enable <效果>` | 启用一个效果（将持续为玩家提供某效果） |
| `/surkit disable <效果>` | 禁用一个效果（将立即移除每个玩家的对应效果，且不再自动给予；但玩家仍可自行通过合成药水获得） |
| `/surkit levelset <效果> <等级>` | 设置一个效果的等级（等级必须是 1-255 之间的整数） |
| `/surkit list` | 列出所有已启用的效果及其等级 |

---

# MiniSurvivalKit
This plugin adds several handy utilities to survival servers, aiming to improve administrative efficiency and enhance player experience.
Currently, it provides automatic potion effect application, with more features planned for the future.

Supported versions: Tested on Paper 1.21.1 (theoretically supports Spigot/Paper 1.19–1.21.4; please test in your own environment before use).

# Introduction
/surkit is the main command.

Currently only the potion effect module is available, with the following subcommands:

| Command | Description |
|---------|-------------|
| `/surkit enable <effect>` | Enables an effect (will continuously provide the effect to all players) |
| `/surkit disable <effect>` | Disables an effect (immediately removes it from all players and stops auto-granting; players can still obtain it via brewing) |
| `/surkit levelset <effect> <level>` | Sets the level of an effect (level must be an integer between 1 and 255) |
| `/surkit list` | Lists all enabled effects and their levels |
