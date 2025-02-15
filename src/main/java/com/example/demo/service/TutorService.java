package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tutor;
import com.example.demo.repository.TutorRepository;

@Service
public class TutorService {
	
	@Autowired
	private TutorRepository repo;
	
	/*public List<TutorProjection> listAll() {
	    return repo.listAll(); // This will now return only the fields in TutorProjection
	}*/
	
	public List<Tutor> listAll(){
		
		 return repo.findAll();
	}
	
	public List<Tutor> getRandomTutors() {
	    // Get all tutors
	    List<Tutor> allTutors = repo.findAll();

	    // Return the first 8 tutors (or fewer if there are not enough)
	    return allTutors.subList(0, Math.min(5, allTutors.size()));
	    
	}
	
	public void save(Tutor msc) {
		
		  repo.save(msc);
	}
	
	public void delete(Tutor msc) {
		
		repo.delete(msc);
		
	}
	
	public void update(String id , Tutor updatedTutor) {
	       
		 Optional<Tutor> tutor = repo.findById(updatedTutor.getEmail());
		 
		 if (tutor.isPresent()){
			 
			 Tutor existingTutor = tutor.get();

			    existingTutor.setFullNames(updatedTutor.getFullNames());
			    existingTutor.setImage(updatedTutor.getImage());
			    existingTutor.setPhoneNumber(updatedTutor.getPhoneNumber());
			    existingTutor.setEmail(updatedTutor.getEmail());
			    existingTutor.setAddress(updatedTutor.getAddress());
			    existingTutor.setSubjects(updatedTutor.getSubjects());
			    existingTutor.setGrades(updatedTutor.getGrades());
			    existingTutor.setSyllabus(updatedTutor.getSyllabus());
			    existingTutor.setAvailability(updatedTutor.getAvailability());
			    existingTutor.setBio(updatedTutor.getBio());
			    existingTutor.setAbout(updatedTutor.getAbout());
			    existingTutor.setHoursTutored(updatedTutor.getHoursTutored());
			    existingTutor.setBackground("Yes");
			    existingTutor.setArea(updatedTutor.getArea());
			    existingTutor.setCountry(updatedTutor.getCountry());

	            // Save the updated student object
	            repo.save(existingTutor);
	            
         }
	        
	 }
	
	public void updateRatings(String email , int rate) {
		
		String noSpaces = email.replaceAll("\\s+", "");
		
		 Optional<Tutor> tutor = repo.findById(noSpaces);
		 
		 if (tutor.isPresent()){
			 
			 Tutor existingTutor = tutor.get();
			 
			 int ratings = existingTutor.getRatings();
			   ratings += 0;
			 
			 existingTutor.setRatings(ratings);
			 			 
			 repo.save(existingTutor);
			 
		 }
		
	}

	public Page<Tutor> findPage(int pageNumber){
		
	    Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by(Sort.Order.desc("ratings")));
	    return repo.findAll(pageable);
	    
	}
	
	/*TUTORS BY LOCATION*/
	

	 public Page<Tutor> paginateTutors(List<Tutor> tutors, int currentPage) {
	     int pageSize = 10; 
	     int start = Math.max((currentPage - 1) * pageSize, 0);
	     int end = Math.min(start + pageSize, tutors.size());
	     List<Tutor> pageContent = tutors.subList(start, end);

	     // Return a paginated page of tutors
	     return new PageImpl<>(pageContent, PageRequest.of(currentPage - 1, pageSize), tutors.size());
	 }
	 
	 public Page<Tutor> viewProfile(int pageNumber) {
		    Pageable pageable = PageRequest.of(pageNumber - 1, 5); // No sorting
		    return repo.findAll(pageable);
		}
	 
	 public List<Tutor> listAllByLocation(String location){
		    return repo.findTutorsByLocation(location);
		}
		
	 

	
	
		
}