import requests
import json


def req(api, payload):
    url = 'http://localhost:8090'
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url + api, data=json.dumps(payload), headers=headers)
    data = {}
    if response.text != '':
        data['item'] = json.loads(response.text)
    data['status_code'] = response.status_code
    return data


def result(name, expected, real):
    if expected == real:
        print(name + ": pass")
    else:
        print(name + ": fail !")


def result_not_null(name, item):
    if item is not None:
        print(name + ": pass")
    else:
        print(name + ": fail !")


print('------------USER API TEST-----------')
# login
data = req('/user/login', {'number': '001', 'password': '123'})
result_not_null("login_should_be_success", data['item'])

# login with wrong password
data = req('/user/login', {'number': '001', 'password': 'wrong'})
result("login_with_wrong_password", data['status_code'], 401)

# login with a undefined student
data = req('/user/login', {'number': '001111', 'password': '123'})
result("login_with_a_undefined_student", data['status_code'], 401)

# register
data = req('/user/register', {'number': 'py001', 'name': 'pytest', 'major': 'software', 'password': '123'})
result_not_null("register_should_be_success", data['item'])

# register with a used number
data = req('/user/register', {'number': '001', 'name': 'pytest', 'major': 'software', 'password': '123'})
result("register_with_used_number", data['status_code'], 400)

# test stu info
data = req('/stu/info', {'number': '001'})
result_not_null("student_info_should_be_success", data['item'])

# test undefined stu info
data = req('/stu/info', {'number': '0011111'})
result("undefined_student_info", data['status_code'], 400)

# add_to_blacklist
data = req('/admin/blacklistAdd', {'number': '001'})
result("add_to_blacklist", data['status_code'], 200)

# del_from_blacklist
data = req('/admin/blacklistDel', {'number': '001'})
result("del_from_blacklist", data['status_code'], 200)

print('------------RESOURCE API TEST-----------')

# stu get study room list
data = req('/stu/studyRoomList', {})
result("stu_get_study_room_list", data['status_code'], 200)

# stu get study room list
data = req('/admin/studyRoomList', {})
result("admin_get_study_room_list", data['status_code'], 200)

# change Study Room Status
data = req('/admin/changeStudyRoomStatus', {'id': '1', 'status': 1})
result("change_study_room_status", data['status_code'], 200)

# add Study Room Status
data = req('/admin/studyRoomAdd',
           {
               "campus": 1,
               "region": 101,
               "building": 101001,
               "number": "1091",
               "openTime": "08:00",
               "closeTime": "12:00",
               "seatsInfo": [
                   {
                       "row": "1",
                       "column": "1",
                       "hasSocket": True,
                       "status": 1
                   }
               ]
           })
result("add_study_room", data['status_code'], 200)
