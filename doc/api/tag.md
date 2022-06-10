## 标签

标签信息用于笔记页面的显示以及标签页面的显示，用于对笔记进行标签分类以及通过标签进行搜索。

- GET /tag/list
- POST /tag/add
- POST /tag/update
- DELETE /tag/delete/{tagId}

## 接口

### GET　/tag/list

获取全部标签信息


#### 响应示例

```json
{
  "message": "所有标签",
  "data": [
    {
      "id": 1,
      "name": "计算机"
    },
    {
      "id": 2,
      "name": "高数"
    }
  ]
}
```

### POST　/tag/add

添加标签信息


#### 响应示例

```json
{
  "message": "标签成功添加",
  "data": null
}
```

### POST　/tag/update

修改标签信息


#### 响应示例

```json
{
  "message": "标签更新成功",
  "data": null
}
```

### DELETE　/tag/delete/{tagId}

删除标签信息


#### 响应示例

```json
{
  "message": "标签删除成功",
  "data": null
}
```
