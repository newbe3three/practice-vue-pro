import request from '@/utils/request'

// 创建个人技能
export function createUserSkill(data) {
  return request({
    url: '/system/user-skill/create',
    method: 'post',
    data: data
  })
}

// 更新个人技能
export function updateUserSkill(data) {
  return request({
    url: '/system/user-skill/update',
    method: 'put',
    data: data
  })
}

// 删除个人技能
export function deleteUserSkill(id) {
  return request({
    url: '/system/user-skill/delete?id=' + id,
    method: 'delete'
  })
}

// 获得个人技能
export function getUserSkill(id) {
  return request({
    url: '/system/user-skill/get?id=' + id,
    method: 'get'
  })
}

// 获得个人技能分页
export function getUserSkillPage(query) {
  return request({
    url: '/system/user-skill/page',
    method: 'get',
    params: query
  })
}

// 导出个人技能 Excel
export function exportUserSkillExcel(query) {
  return request({
    url: '/system/user-skill/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
