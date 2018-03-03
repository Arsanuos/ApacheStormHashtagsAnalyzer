def get_db(db_name):
    from pymongo import MongoClient
    client = MongoClient('localhost:27017')
    db = client[db_name]
    return db

def insert(db, fileName):
    import json
    saveTo = open("result.json", "w")
    toFile = []
    i = 0;
    with open(fileName) as file:
        headers = file.readline()
        headers = headers.strip()
        headers = headers.split()
        for line in file:
            line = line.strip()
            data = {}
            content = line.split()
            name = content[3].strip()
            if len(content) > 3:
                name = "";
                for index in range(len(content)):
                    if index >= 3:
                        name = name + " " + content[index].strip()
            data[headers[3]] = name.strip()
            data["loc"] = []
            data["loc"].append(float(content[2].strip()))
            data["loc"].append(float(content[1].strip()))
            print(data)
            toFile.append(data)
            i += 1

    json.dump(toFile, saveTo,indent=4)
    print(i)
    saveTo.close()

if __name__ == '__main__':
    db = get_db('stormDB')
    insert(db, "countries.tsv")
