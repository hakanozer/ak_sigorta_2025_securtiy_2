using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using RestApi.Data;
using RestApi.Models;
using System.Linq;

namespace RestApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    [Authorize(Roles = "Note")]
    public class NoteController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public NoteController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public IActionResult Create(Note note)
        {
            _context.Notes.Add(note);
            _context.SaveChanges();
            return Ok(note);
        }

        [HttpPut("{id}")]
        public IActionResult Update(int id, Note note)
        {
            var existingNote = _context.Notes.Find(id);
            if (existingNote == null)
            {
                return NotFound();
            }
            existingNote.Content = note.Content;
            _context.SaveChanges();
            return Ok(existingNote);
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var note = _context.Notes.Find(id);
            if (note == null)
            {
                return NotFound();
            }
            _context.Notes.Remove(note);
            _context.SaveChanges();
            return Ok();
        }

        [HttpGet]
        public IActionResult GetAll()
        {
            // select * from users where email = '"+email+"' and password = '"+password+"'
            // select * from users where email = 'a@a.com' and password = '' or 1 = 1 --'
            var notes = _context.Notes.ToList();
            return Ok(notes);
        }
    }
}