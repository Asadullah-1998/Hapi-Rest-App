import csv
import urllib.request, json


required_val = [['2093-3',"Total Cholesterol"],['39156-5',"BMI"],['2571-8',"Triglycerides"],['6298-4',"Potassium"],['2339-0',"Glucose"],['4548-4',"Hemoglobin A1C"],['32623-1',"Platelet mean volume"]]



root_url = 'https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/'

patients_url = root_url +"Patient/1"

get_variable = "&code="


def column(matrix, i):
    return [row[i] for row in matrix]



def run():
    

    count = 1
    n = 0

    employee_file = open('cholesterol.csv', mode='w')
    employee_writer = csv.writer(employee_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)

    employee_writer.writerow((["Patient No"]+column(required_val,1)+['Age', 'Gender', 'ActualValue','Tobacco Smoker']))
    
    total_count = 0
        
    
    while total_count < 500:
        inner_count = 0
        values = []

        json_url1 = urllib.request.urlopen(root_url+"Observation?patient="+str(count))
        data1 = json.loads(json_url1.read())

        if ('total' in data1):
            if (data1['total'] >= 1):
                for x in required_val:
                    

                    json_url = urllib.request.urlopen(root_url+"Observation?patient="+str(count)+get_variable+x[0]+"&_sort=-date")#get_variable+required_val[0])

                    data = json.loads(json_url.read())

                    if (data['total'] == 0):
                        values.append("null")
                    else:
                        entry = data['entry']
                        item = entry[0]['resource']
                        val = item['valueQuantity']['value']
                        values.append(val)
                        inner_count += 1

                if inner_count >= 3 and values[0] != "null":
                    json_url3 = urllib.request.urlopen(root_url+"Patient/"+str(count))
                    data3 = json.loads(json_url3.read())
                    if ('gender' in data3):
                        gender = data3['gender']
                    else:
                        gender = "null"
                    if ('birthDate' in data3):
                        bd = data3['birthDate']
                        age = 2020 - int(bd[0:4])
                    else:
                        age = "null"
                    values.append(age)
                    values.append(gender)
                    if (values[0] > 200):
                        values.append("IsHigh")
                    else:
                        values.append("IsLow")

                    json_url2 = urllib.request.urlopen(root_url+"Observation?patient="+str(count)+get_variable+'72166-2'+"&_sort=-date")

                    data2 = json.loads(json_url2.read())

                    if ('total' in data2):
                        if (data2['total'] >= 1):
                            entry = data2['entry']
                            item = entry[0]['resource']
                            val = item['valueCodeableConcept']['text']
                            values.append(val)
                        else:
                            values.append("null")
                            
                    
                    print(count, total_count)

                    employee_writer = csv.writer(employee_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
                    employee_writer.writerow([count]+values)
                    
                    total_count += 1
                    count += 1
                else:
                    count += 1

            else:
                count += 1
        else:
            count += 1



run()
