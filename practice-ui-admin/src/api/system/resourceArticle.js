import request from '@/utils/request'

// 创建文章资源
export function createResourceArticle(data) {
  return request({
    url: '/system/resource-article/create',
    method: 'post',
    data: data
  })
}

// 更新文章资源
export function updateResourceArticle(data) {
  return request({
    url: '/system/resource-article/update',
    method: 'put',
    data: data
  })
}

// 删除文章资源
export function deleteResourceArticle(id) {
  return request({
    url: '/system/resource-article/delete?id=' + id,
    method: 'delete'
  })
}

// 获得文章资源
export function getResourceArticle(id) {
  return request({
    url: '/system/resource-article/get?id=' + id,
    method: 'get'
  })
}

// 获得文章资源分页
export function getResourceArticlePage(query) {
  return request({
    url: '/system/resource-article/page',
    method: 'get',
    params: query
  })
}

// 导出文章资源 Excel
export function exportResourceArticleExcel(query) {
  return request({
    url: '/system/resource-article/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
