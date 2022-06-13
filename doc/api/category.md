## 分类

分类信息用于笔记页面的显示以及分类界面的内容显示。

- GET /category/list/all
- POST /category/add
- POST /category/update
- DELETE /category/delete/{categoryId}
- GET /category/list/used

## 接口

### GET　/category/list/all

获取所有笔记分类


#### 响应示例

```json
{
  "message": "所有笔记分类",
  "data": [
    {
      "id": 0,
      "name": "未分类",
      "description": "未分类",
      "noteCount": 5
    },
    {
      "id": 6,
      "name": "uod",
      "description": "asda",
      "noteCount": 0
    }
  ]
}
```

### POST　/category/add

添加笔记分类

#### 参数信息

| 参数          | 类型      | 必填  | 释义   | 合法值                 |
|-------------|---------|-----|------|---------------------|
| id          | integer | 否   | 分类编号 |                     |
| name        | String  | 否   | 分类名称 |                     |
| description | String  | 否   | 分类描述 |                     |


#### 响应示例

```json
{
  "message": "添加分类成功",
  "data": null
}
```

### POST　/category/update

修改分类信息

#### 参数信息

| 参数          | 类型      | 必填  | 释义   | 合法值 |
|-------------|---------|-----|------|-----|
| id          | integer | 是   | 分类编号 |     |
| name        | String  | 否   | 分类名称 |     |
| description | String  | 否   | 分类简述 |     |


#### 响应示例

```json
{
  "message": "更新分类成功",
  "data": null
}
```

### DELETE　/category/delete/{noteId}

删除分类信息


#### 响应示例

```json
{
  "message": "删除分类成功",
  "data": null
}
```

### GET　/category/list/used

获取已用笔记分类


#### 响应示例

```json
{
  "message": "已使用的笔记分类",
  "data": [
    {
      "id": 0,
      "name": "未分类",
      "description": "未分类",
      "noteCount": 5
    }
  ]
}
```
