package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class EmployeeService {

	private static final String COLLECTION_NAME = "employee";

	public String saveEmployee(Employee employee) throws InterruptedException, ExecutionException {

		Firestore dbFirestore = FirestoreClient.getFirestore();
		Employee emp = getEmployeeDetails(employee.getId());
		if(emp == null) {
			ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(employee.getId()).set(employee);
			return collectionApiFuture.get().getUpdateTime().toString();
		}
		return "Record Already Exist";
	}

	public Employee getEmployeeDetails(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> documentApiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = documentApiFuture.get();

		Employee employee = null;
		if(documentSnapshot.exists()) {
			employee = documentSnapshot.toObject(Employee.class);
			return employee;
		} else {
			return null;
		}
	}

	public List<Employee> getAllEmployeeDetails() throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Iterable<DocumentReference> iterableDocumentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();

		Iterator<DocumentReference> iterator = iterableDocumentReference.iterator();

		List<Employee> employeeList = new ArrayList<>();
		Employee employee = null;
		while(iterator.hasNext()) {
			DocumentReference documentReference = iterator.next();
			ApiFuture<DocumentSnapshot> documentApiFuture = documentReference.get();
			DocumentSnapshot documentSnapshot = documentApiFuture.get();
			employee = documentSnapshot.toObject(Employee.class);
			employeeList.add(employee);
		}
		return employeeList;
	}

	public String updateEmployee(Employee employee) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(employee.getId()).set(employee);
		return collectionApiFuture.get().getUpdateTime().toString();
	}
	
	public String deleteEmployee(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Employee emp = getEmployeeDetails(id);
		if(emp != null) {
			dbFirestore.collection(COLLECTION_NAME).document(id).delete();
			return "Document with Employee ID " +id+ " has been deleted successfully";
		}
		return "No Record Found";
	}
}
