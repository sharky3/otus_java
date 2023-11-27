package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final Comparator<Customer> comparator = Comparator.comparingLong(Customer::getScores);

    private final NavigableMap<Customer, String> customersStorage = new TreeMap<>(comparator);

    public Map.Entry<Customer, String> getSmallest() {
        var firstEntry = customersStorage.firstEntry();
        return copyOrNull(firstEntry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var nextEntry = customersStorage.higherEntry(customer);
        return copyOrNull(nextEntry);
    }

    public void add(Customer customer, String data) {
        customersStorage.put(customer, data);
    }

    private Map.Entry<Customer, String> copyOrNull(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        } else {
            var entryKey = entry.getKey();
            var customerCopy = new Customer(entryKey.getId(), entryKey.getName(), entryKey.getScores());
            return Map.entry(customerCopy, entry.getValue());
        }
    }
}
