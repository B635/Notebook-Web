## 笔记

笔记信息用于笔记页面的显示以及写笔记页面的内容显示。

- POST /note/list
- POST /note/add
- POST /note/update
- DELETE /note/delete/{noteId}
- GET /note/get/{id}

## 接口

### POST　/note/list

获取分页笔记简单信息


#### 参数信息

| 参数         | 类型      | 必填  | 释义   | 合法值                 |
|------------|---------|-----|------|---------------------|
| current    | integer | 是   | 当前页码 |                     |
| pageSize   | integer | 是   | 每页数量 |                     |
| title      | String  | 否   | 笔记标题 |                     |
| categoryId | integer | 否   | 笔记分类 |                     |
| status     | String  | 否   | 笔记状态 | SAVED,DRAFT,RECYCLE |

#### 响应示例


```json
{
  "message": "笔记",
  "data": {
    "total": 4,
    "list": [
      {
        "id": 13,
        "title": "电子",
        "category": {
          "id": 0,
          "name": "未分类",
          "description": "未分类",
          "noteCount": 5
        },
        "label": [],
        "status": "SAVED",
        "summary": "sads",
        "date": "2022-06-13T03:18:46.710466Z"
      },
      {
        "id": 11,
        "title": "电子",
        "category": {
          "id": 0,
          "name": "未分类",
          "description": "未分类",
          "noteCount": 5
        },
        "label": [],
        "status": "SAVED",
        "summary": "sads",
        "date": "2022-06-10T09:31:34.729591Z"
      }
    ]
  }
}
```

### POST　/note/add

添加笔记信息

#### 参数信息

| 参数       | 类型      | 必填  | 释义   | 合法值                 |
|----------|---------|-----|------|---------------------|
| id       | integer | 否   | 笔记编号 |                     |
| title    | String  | 否   | 笔记标题 |                     |
| content  | String  | 否   | 笔记内容 |                     |
| category | json    | 否   | 笔记分类 | 详见分类文档              |
| summary  | String  | 否   | 笔记简介 |                     |
| status   | String  | 否   | 笔记状态 | SAVED,DRAFT,RECYCLE |
| label    | array   | 否   | 笔记标签 |                     |

#### 响应示例

```json
{
  "message": "发表博客文章成功",
  "data": null
}
```

### POST　/tag/update

修改笔记信息

#### 参数信息

| 参数       | 类型      | 必填  | 释义   | 合法值                 |
|----------|---------|-----|------|---------------------|
| id       | integer | 是   | 笔记编号 |                     |
| title    | String  | 否   | 笔记标题 |                     |
| content  | String  | 否   | 笔记内容 |                     |
| category | json    | 否   | 笔记分类 | 详见分类文档              |
| summary  | String  | 否   | 笔记简介 |                     |
| status   | String  | 否   | 笔记状态 | SAVED,DRAFT,RECYCLE |
| label    | array   | 否   | 笔记标签 |                     |

#### 响应示例

```json
{
  "message": "笔记更新成功",
  "data": null
}
```

### DELETE　/note/delete/{noteId}

删除笔记信息


#### 响应示例

```json
{
  "message": "删除笔记成功",
  "data": null
}
```

### GET　/note/get/{id}

获取笔记内容


#### 响应示例

```json
{
  "message": "笔记信息",
  "data": {
    "id": 9,
    "title": "电子",
    "category": {
      "id": 0,
      "name": "未分类",
      "description": "未分类",
      "noteCount": 4
    },
    "label": [],
    "status": "SAVED",
    "summary": "sads",
    "date": "2022-06-10T09:24:29.119497Z",
    "content": null
  }
}
```
