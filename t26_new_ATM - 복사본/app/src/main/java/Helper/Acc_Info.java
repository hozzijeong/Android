package Helper;

public class Acc_Info {
    public String host_acc,host_name,client_acc,client_name,total,transfer;

    public Acc_Info(String host_acc, String host_name, String client_name, String client_acc, String transfer, String total){
        this.host_acc   = host_acc;
        this.host_name  = host_name;
        this.client_acc = client_acc;
        this.client_name =client_name;
        this.transfer = transfer;
        this.total = total;
    }
}
