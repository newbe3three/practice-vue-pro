import request from '@/utils/request'

////////////////////////////////////////////////平台端-实践管理////////////////////////////////////////////////////////////

// 获得实践分页
export function getPracticePage(query) {
  return request({
    url: '/system/practice/page',
    method: 'get',
    params: query
  })
}

// 实践审核-平台-查询到驳回记录
export function reviewPractice(id) {
  return request({
    url: '/system/practice/review?practiceId=' + id,
    method: 'get',
  })
}

// 审核通过-平台
export function reviewPass(id) {
  return request({
    url: '/system/practice/review/pass?practiceId=' + id,
    method: 'get',
  })
}


// 审核駁回-平台
export function reviewFailurePractice(id, reason) {
  return request({
    url: '/system/practice/review/failure',
    method: 'post',
    data: {
      'practiceId': id,
      'suggestion': reason
    }
  })
}


////////////////////////////////////////////////学校端-实践管理////////////////////////////////////////////////////////////


// 申請實踐-学校
export function applyPracticeSchool(schoolId, practiceId) {
  return request({
    url: '/system/practice-school/school/create',
    method: 'post',
    data: {
      'schoolId': schoolId,
      'practiceId': practiceId
    }
  })
}

// 学校查询本校发起的对实践的申请-学校-对应已申请状态
export function schoolGetPracticeSchoolPage(data) {
  return request({
    url: '/system/practice-school/school/create',
    method: 'get',
    data: data,
  })
}

// 院校端查询审核通过还未确定学校的实践
export function schoolQueryPractice(data) {
  return request({
    url: '/system/practice/school/page',
    method: 'get',
    data: data,
  })
}

////////////////////////////////////////////////企业端-实践管理////////////////////////////////////////////////////////////


// 获得本公司实践列表
export function companyGetPracticePage(data) {
  return request({
    url: '/system/practice/company/page',
    method: 'get',
    data: data,
  })
}


// 创建实践
export function companyCreatePractice(data) {
  return request({
    url: '/system/practice/company/create',
    method: 'post',
    data: data
  })
}

// 更新实践
export function companyUpdatePractice(data) {
  return request({
    url: '/system/practice/company/update',
    method: 'put',
    data: data
  })
}

// 企业修改实践状态为已经选定学校
export function companyConfirmPractice(id) {
  return request({
    url: '/system/practice/delete?id=' + id,
    method: 'delete'
  })
}






